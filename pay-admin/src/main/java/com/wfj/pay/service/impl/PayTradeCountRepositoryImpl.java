package com.wfj.pay.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Order;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.dto.PaginationDTO;
import com.wfj.pay.dto.PayMentDateDTO;
import com.wfj.pay.dto.StatisticsDTO;
import com.wfj.pay.dto.StatisticsRequestDTO;
import com.wfj.pay.dubbo.IOPSOperationDubbo;
import com.wfj.pay.po.PayMerchantPO;
import com.wfj.pay.service.PayTradeCountRepository;

@Service
public class PayTradeCountRepositoryImpl implements PayTradeCountRepository{
	private static final Logger LOGGER = LoggerFactory.getLogger(PayTradeCountRepositoryImpl.class);

	@Reference(version="1.0.0", cluster = "failfast")
	private IOPSOperationDubbo opsDubbo;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	/**
	 * 带过滤条件统计订单金额
	 * @param payTypes  支付渠道集合
	 * @param bpIds		bpId集合
	 * @param startDate 开始时间   不能为空
	 * @param endDate	结束时间   不能为空
	 * @return
	 */
    public List<PayMentDateDTO> selectPayTradeCount(List<String> payTypes, List<String> merCodes, Long startDate, Long endDate){
    	List<PayMentDateDTO> mentDateList = new ArrayList<PayMentDateDTO>();
    	//正向订单
    	Map<String, Aggregation> payTradeAggMap = payTradeCount(payTypes, merCodes, startDate, endDate);
    	//你想订单
    	Map<String, Aggregation> payRefundTradeAggMap = payRefundTrade(payTypes, merCodes, startDate, endDate);
    	
    	//通过别名获取正向统计数据
    	StringTerms payTradeData = (StringTerms) payTradeAggMap.get("groupByMerCode");
    	//通过别名获取逆向统计数据
    	StringTerms payRefundTradeData = (StringTerms) payRefundTradeAggMap.get("groupByMerCode");
    	
    	//获取分组统计正向数据的集合
		List<Bucket> payTradeBuckets = payTradeData.getBuckets();
		//获取分组统计逆向数据的集合
		List<Bucket> payRefundTradeBuckets = payRefundTradeData.getBuckets();
		
		//从数据库中查询所有门店name
		List<PayMerchantPO> merchantAll = opsDubbo.findMerchantByMerCode(merCodes);
		getPayMentDate(mentDateList, merchantAll, payTradeBuckets, payRefundTradeBuckets);
		
    	return mentDateList;
    }
    
	/**
     * 正向订单
     * @param payTypes
     * @param bpIds
     * @param startDate
     * @param endDate
     * @return
     */
    public Map<String, Aggregation> payTradeCount(List<String> payTypes, List<String> merCodes, Long startDate, Long endDate) {
    	//获取SearchRequestBuilder连接   放入条件后可以看到生成的es查询语句
    	SearchRequestBuilder sbuilder = elasticsearchTemplate.getClient().prepareSearch("pay-data").setTypes("pay-trade").setSearchType("count");
    	String rangeName = "createDate";
    	setQueryParams(sbuilder, payTypes, merCodes, rangeName, startDate, endDate);
    	
    	//获得TermsBuilder  aggregation是聚合  terms相当于数据库中的group by （此处的terms后面括号里的内容是别名，可以通过别名获取聚合数据， field后的内容是要聚合分组的字段名）
    	//order里的内容排序字段（_term是按前面field的字段排序，默认是按分组后每组count(总数)倒序）  后面的boolean值代表排序方式  true是正序  false是倒序   size是返回聚合条数  默认是10条
    	TermsBuilder teamAgg = AggregationBuilders.terms("groupByMerCode").field("merCode").order(Order.aggregation("_term", true)).size(100);
    	//统计支付金额   sum是求和（此处的sum后面括号里的内容是别名，可以通过别名获取求和后数据， field后的内容是求和依据的字段名）
    	SumBuilder totalFeeAgg = AggregationBuilders.sum("total_fee").field("totalFee");
    	//统计优惠金额
    	SumBuilder couponFeeAgg = AggregationBuilders.sum("coupon_fee").field("couponFee");
    	//添加聚合条件   在上面的查询条件后拼接聚合条件
    	//SumBuilder可以放入TermsBuilder分组条件中使用  统计的是每组的和。  也可以单独使用  统计的是查询条件内的所有数据的和
    	sbuilder.addAggregation(teamAgg.subAggregation(totalFeeAgg).subAggregation(couponFeeAgg));
    	
    	LOGGER.info("正向订单统计语句------" + sbuilder.toString());
    	//获取返回数据
    	SearchResponse response = sbuilder.execute().actionGet();
    	//获得聚合内容
    	Map<String, Aggregation> aggMap = response.getAggregations().asMap();
    	return aggMap;
    }
    
    /**
     * 逆向订单
     * @param payTypes
     * @param bpIds
     * @param startDate
     * @param endDate
     * @return
     */
    public Map<String, Aggregation> payRefundTrade(List<String> payTypes, List<String> merCodes, Long startDate, Long endDate) {
    	//获取SearchRequestBuilder连接   放入条件后可以看到生成的es查询语句
    	SearchRequestBuilder sbuilder = elasticsearchTemplate.getClient().prepareSearch("pay-data").setTypes("pay-refund-trade").setSearchType("count");
    	
    	String rangeName = "createOrderTime";
    	setQueryParams(sbuilder, payTypes, merCodes, rangeName, startDate, endDate);
    	
    	//获得TermsBuilder  aggregation是聚合  terms相当于数据库中的group by （此处的terms后面括号里的内容是别名，可以通过别名获取聚合数据， field后的内容是要聚合分组的字段名）
    	//order里的内容排序字段（_term是按前面field的字段排序，默认是按分组后每组count(总数)倒序）  后面的boolean值代表排序方式  true是正序  false是倒序   size是返回聚合条数  默认是10条
    	TermsBuilder teamAgg = AggregationBuilders.terms("groupByMerCode").field("merCode").order(Order.aggregation("_term", true)).size(100);
    	//统计支付金额   sum是求和（此处的sum后面括号里的内容是别名，可以通过别名获取求和后数据， field后的内容是求和依据的字段名）
    	SumBuilder refundFeeAgg = AggregationBuilders.sum("refund_fee").field("refundFee");
    	//添加聚合条件   在上面的查询条件后拼接聚合条件
    	//SumBuilder可以放入TermsBuilder分组条件中使用  统计的是每组的和。  也可以单独使用  统计的是查询条件内的所有数据的和
    	sbuilder.addAggregation(teamAgg.subAggregation(refundFeeAgg));
    	
    	LOGGER.info("逆向订单统计语句------" + sbuilder.toString());
    	//获取返回数据
    	SearchResponse response = sbuilder.execute().actionGet();
    	//获得聚合内容
    	Map<String, Aggregation> aggMap = response.getAggregations().asMap();
    	return aggMap;
    }
    
    /**
     * 添加查询过滤条件
     * @param sbuilder  SearchRequestBuilder
     * @param payTypes  支付渠道集合
	 * @param bpIds		bpId集合
	 * @param rangeName 返回查询字段名
	 * @param startDate 开始时间   不能为空
	 * @param endDate	结束时间   不能为空
     */
    public void setQueryParams(SearchRequestBuilder sbuilder, List<String> payTypes, List<String> merCodes, String rangeName, Long startDate, Long endDate) {
    	//查询条件BoolQueryBuilder类
    	BoolQueryBuilder queryBool = QueryBuilders.boolQuery();
    	//判断支付渠道是否存在
    	if (payTypes != null && payTypes.size() > 0) {
    		//bool中的shoule查询方式   
    		BoolQueryBuilder payTypeShould = QueryBuilders.boolQuery();
    		payTypes.forEach(payType -> {
    			//遍历支付渠道   查询条件是或的关系 should是一个数组，里面有多个match  每个match是或的管理   should相当于数据库中的in
    			payTypeShould.should(QueryBuilders.matchQuery("payType", payType));
    		});
    		//把支付渠道条件添加到BoolQueryBuilder的must中  should是一个数组  里面可以添加多重查询条件
    		queryBool.must(payTypeShould);
    	}
    	//判断mercode是否存在
    	if (merCodes != null && merCodes.size() > 0) {
    		//同payType   可以放一块   因为每个match中会分清楚查询的键是bpid还是payType
    		BoolQueryBuilder bpIdShould = QueryBuilders.boolQuery();
    		merCodes.forEach(merCode -> {
    			bpIdShould.should(QueryBuilders.matchQuery("merCode", merCode));
    		});
    		queryBool.must(bpIdShould);
    	}
    	//range是范围查询 from开始 to结束
    	RangeQueryBuilder dateRang = QueryBuilders.rangeQuery(rangeName).from(startDate).to(endDate).includeLower(true).includeUpper(true);
    	queryBool.must(dateRang);
    	//支付状态为2
    	MatchQueryBuilder statusMatch = QueryBuilders.matchQuery("status", 2L);
    	queryBool.must(statusMatch);
    	//把查询条件添加到SearchRequestBuilder连接中   生成es语句
    	sbuilder.setQuery(queryBool);
    }
    
    /**
     * 组装PayMentDateDTO List
     * @param mentDateList			PayMentDateDTO集合
     * @param merchantAll			门店信息集合
     * @param payTradeBuckets		正向订单集合
     * @param payRefundTradeBuckets	逆向订单集合
     */
    private void getPayMentDate(List<PayMentDateDTO> mentDateList, List<PayMerchantPO> merchantAll,
			List<Bucket> payTradeBuckets, List<Bucket> payRefundTradeBuckets) {
    	PayMentDateDTO mentDate = null;
    	int t = 0;
    	int r = 0;
    	//merchantAll、 payTradeBuckets、payRefundTradeBuckets都是按门店号正向排序  所以顺序一直， 当门店号不匹配说明该该门店该项内容为空
    	//所有集合都是按门店号正序排序
    	for (int i = 0; i < merchantAll.size(); i++) {
			mentDate = new PayMentDateDTO();
			PayMerchantPO merchat = merchantAll.get(i);
			mentDate.setStoreNo(merchat.getMerCode());
			mentDate.setStoreName(merchat.getName());
			
			//保存正向订单信息
			if (t < payTradeBuckets.size()) {
				Bucket payTradeBuck = payTradeBuckets.get(t);
				if (merchat.getMerCode().equals(payTradeBuck.getKeyAsString())) {
					mentDate.setPayToalCount(payTradeBuck.getDocCount());
					Map<String, Aggregation> asMap = payTradeBuck.getAggregations().asMap();
					mentDate.setPayTotalFee(((InternalSum) asMap.get("total_fee")).getValue());
					mentDate.setCouponTotalFee(((InternalSum) asMap.get("coupon_fee")).getValue());
					t++;
				}
			}
			//保存你想订单信息
			if (r < payRefundTradeBuckets.size()) {
				Bucket payRefundTradeBuck = payRefundTradeBuckets.get(r);
				if (merchat.getMerCode().equals(payRefundTradeBuck.getKeyAsString())) {
					mentDate.setRefundTotalCount(payRefundTradeBuck.getDocCount());
					Map<String, Aggregation> asMap = payRefundTradeBuck.getAggregations().asMap();
					mentDate.setRefundTotalFee(((InternalSum) asMap.get("refund_fee")).getValue());
					r++;
				}
			}
			mentDateList.add(mentDate);
		}
	}

    /**
     * 查询符合条件的订单总数
     */
	@Override
	public Long getTradeCountByParams(OrderQueryReqDTO reqDTO) {
		//获取SearchRequestBuilder连接   放入条件后可以看到生成的es查询语句
    	SearchRequestBuilder sbuilder = elasticsearchTemplate.getClient().prepareSearch("pay-data").setTypes("pay-trade").setSearchType("count");
    	
    	//设置查询参数
    	BoolQueryBuilder queryBool = setESQueryParams(reqDTO);
    	//把查询条件添加到SearchRequestBuilder连接中   生成es语句
    	sbuilder.setQuery(queryBool);
    	LOGGER.info("符合条件订单查询语句------" + sbuilder.toString());
    	//获取返回数据
    	SearchResponse response = sbuilder.execute().actionGet();
    	//获得聚合内容
    	Long count = response.getHits().getTotalHits();
		return count;
	}
	
	@Override
	public PaginationDTO<StatisticsDTO> searchlist(StatisticsRequestDTO reqDTO) {
		PaginationDTO<StatisticsDTO> pagination = new PaginationDTO<StatisticsDTO>();
		if (reqDTO.getPageNo().intValue() <= 0
				|| reqDTO.getPageSize().intValue() <= 0) {
			LOGGER.error("分页查询参数：");
			LOGGER.error("pageNo:" + reqDTO.getPageNo());
			LOGGER.error("pageSize:" + reqDTO.getPageSize());
			throw new RuntimeException("分页参数异常");
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String start = format.format(reqDTO.getStartTime());
			Date startDay = format.parse(start);
			Long startTimeC = DateUtils.addYears(startDay, 1).getTime();
			if (reqDTO.getEndTime() > startTimeC) {
				LOGGER.error("时间差：" + (reqDTO.getEndTime() - reqDTO.getStartTime()));
				throw new RuntimeException("所选时间跨度超过1年");
			}
		} catch (Exception e) {
			LOGGER.error(e.toString(),e);
			throw new RuntimeException(e);
		}
		
		if (reqDTO.getGroupTime().intValue() != 0
				&& reqDTO.getGroupTime().intValue() != 1
				&& reqDTO.getGroupTime().intValue() != 2) {
			LOGGER.error("按所选时间分组参数异常：groupTime=" + reqDTO.getGroupTime());
			throw new RuntimeException("按所选时间分组参数异常");
		}
		if (reqDTO.getStatList().intValue() < 0
				|| reqDTO.getStatList().intValue() > 4) {
			LOGGER.error("按所选统计内容参数异常：groupTime=" + reqDTO.getGroupTime());
			throw new RuntimeException("按所选统计内容参数异常");
		}
		
		Map<String, Aggregation> aggsResult = createPageQuery(reqDTO);
		LongTerms data = (LongTerms) aggsResult.get("groupBy");
		List<Bucket> buckets = data.getBuckets();
		
		List<StatisticsDTO> dataList = getStatisticsDTO(buckets, reqDTO);
		pagination.setPageNo(reqDTO.getPageNo());
		pagination.setPageSize(reqDTO.getPageSize());
		pagination.setTotalHit(buckets.size());
		pagination.calculatePage();
		pagination.setListData(dataList);
		return pagination;
	}

	/**
	 * 构造查询条件.
	 * 
	 * @param dto
	 *            查询参数
	 * @return
	 * @author admin重构，dipin始作俑者
	 * @date 2014-4-8 上午10:01:38
	 */
	private Map<String, Aggregation> createPageQuery(StatisticsRequestDTO reqDTO) {
		/**
		 * 创建订单时间分组字段常量数组（0：按季度，1：按月，2：按日）.
		 */
		String[] CREATE_DATE = { "createDateQuarter",
				"createDateMonth", "createDateDay" };

		//获取SearchRequestBuilder连接   放入条件后可以看到生成的es查询语句
    	SearchRequestBuilder sbuilder = elasticsearchTemplate.getClient().prepareSearch("pay-data").setTypes("pay-trade").setSearchType("count");
    	
    	//查询条件BoolQueryBuilder类
    	BoolQueryBuilder queryBool = QueryBuilders.boolQuery();
    	
    	//status
    	if (reqDTO.getStatList() != 1) {
    		//支付状态  match必须符合该条件
        	MatchQueryBuilder statusMatch = QueryBuilders.matchQuery("status", 2L);
        	queryBool.must(statusMatch);
    	}
    	
    	//bpid
    	if (reqDTO.getBpId() != null && reqDTO.getBpId() != 0L) {
        	MatchQueryBuilder bpIdMatch = QueryBuilders.matchQuery("bpId", reqDTO.getBpId());
        	queryBool.must(bpIdMatch);
    	}
    	
    	
    	//initOrderTerminal, 
    	if (!StringUtils.isBlank(reqDTO.getInitOrderTerminal())) {
    		MatchQueryBuilder orderTradeNoMatch = QueryBuilders.matchQuery("initOrderTerminal", reqDTO.getInitOrderTerminal());
    		queryBool.must(orderTradeNoMatch);
    	}
    	
    	//bpids
    	if (reqDTO.getAllowedBpIds() != null && reqDTO.getAllowedBpIds().size() > 0) {
    		//同payType   可以放一块   因为每个match中会分清楚查询的键是bpid还是payType
    		BoolQueryBuilder bpIdShould = QueryBuilders.boolQuery();
    		reqDTO.getAllowedBpIds().forEach(bpId -> {
    			bpIdShould.should(QueryBuilders.matchQuery("bpId", bpId));
    		});
    		queryBool.must(bpIdShould);
    	}
    	//range是范围查询 from开始 to结束
    	RangeQueryBuilder dateRang = QueryBuilders.rangeQuery("createDate").from(reqDTO.getStartTime()).to(reqDTO.getEndTime()).includeLower(true).includeUpper(true);
    	queryBool.must(dateRang);
    	//把查询条件添加到SearchRequestBuilder连接中   生成es语句
    	sbuilder.setQuery(queryBool);
    	
    	//获得TermsBuilder  aggregation是聚合  terms相当于数据库中的group by （此处的terms后面括号里的内容是别名，可以通过别名获取聚合数据， field后的内容是要聚合分组的字段名）
    	//order里的内容排序字段（_term是按前面field的字段排序，默认是按分组后每组count(总数)倒序）  后面的boolean值代表排序方式  true是正序  false是倒序   size是返回聚合条数  默认是10条
    	TermsBuilder teamAgg = AggregationBuilders.terms("groupBy").field(CREATE_DATE[reqDTO.getGroupTime()]).order(Order.aggregation("_term", false)).size(400);
    	//统计支付金额   sum是求和（此处的sum后面括号里的内容是别名，可以通过别名获取求和后数据， field后的内容是求和依据的字段名）
    	SumBuilder refundFeeAgg = AggregationBuilders.sum("total_fee").field("totalFee");
    	//添加聚合条件   在上面的查询条件后拼接聚合条件
    	//SumBuilder可以放入TermsBuilder分组条件中使用  统计的是每组的和。  也可以单独使用  统计的是查询条件内的所有数据的和
    	sbuilder.addAggregation(teamAgg.subAggregation(refundFeeAgg));
    	
    	LOGGER.info("统计管理统计查询es语句------" + sbuilder.toString());
    	//获取返回数据
    	SearchResponse response = sbuilder.execute().actionGet();
    	//获得聚合内容
    	Map<String, Aggregation> aggMap = response.getAggregations().asMap();
		return aggMap;
	}
	
	/**
	 * 获取查询结果
	 * @param buckets
	 * @param reqDTO
	 * @return
	 */
	private List<StatisticsDTO> getStatisticsDTO(List<Bucket> buckets, StatisticsRequestDTO reqDTO) {
		String bpName = null;
		if (reqDTO.getBpId() != null && reqDTO.getBpId() != 0) {
			bpName = opsDubbo.findBusinessByBpid(reqDTO.getBpId()).getBpName();
		} else {
			bpName = "全业务平台";
		}
		
		//aggs 没有分页功能只能设置查询总数  当前list里包含所有内容
				//数据开始索引
		int start = (reqDTO.getPageNo() - 1) * reqDTO.getPageSize();
		int end = reqDTO.getPageNo() * reqDTO.getPageSize();
		if ((end - start) > buckets.size()) {
			end = buckets.size();
		}
		List<StatisticsDTO> dataList = new ArrayList<StatisticsDTO>();
		for (int i = start; i < end; i++) {
			StatisticsDTO dto = new StatisticsDTO();
			Bucket bucket = buckets.get(i);
			dto.setBpName(bpName);
			dto.setTime(bucket.getKeyAsString());
			if (reqDTO.getStatList() == 0) {
				Map<String, Aggregation> asMap = bucket.getAggregations().asMap();
				String totalFee = String.valueOf(((InternalSum) asMap.get("total_fee")).getValue());
				totalFee = new BigDecimal(totalFee).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
				dto.setData(totalFee);
			}else {
				dto.setData(String.valueOf(bucket.getDocCount()));
			}
			dataList.add(dto);
		}
		return dataList;
	}

	@Override
	public Map<String, Object> verificaMoneyCount(OrderQueryReqDTO reqDTO) {
		//获取SearchRequestBuilder连接   放入条件后可以看到生成的es查询语句
    	SearchRequestBuilder sbuilder = elasticsearchTemplate.getClient().prepareSearch("pay-data").setTypes("pay-trade").setSearchType("count");
    	
    	//查询条件BoolQueryBuilder类
    	BoolQueryBuilder queryBool = setESQueryParams(reqDTO);
    	//把查询条件添加到SearchRequestBuilder连接中   生成es语句
    	sbuilder.setQuery(queryBool);
    	//统计支付金额   sum是求和（此处的sum后面括号里的内容是别名，可以通过别名获取求和后数据， field后的内容是求和依据的字段名）
    	SumBuilder totalFeeAgg = AggregationBuilders.sum("total_fee").field("totalFee");
    	SumBuilder channelFeeAgg = AggregationBuilders.sum("channl_fee_cost").field("channelFeeCost");
    	
    	//添加聚合条件   在上面的查询条件后拼接聚合条件
    	//SumBuilder可以放入TermsBuilder分组条件中使用  统计的是每组的和。  也可以单独使用  统计的是查询条件内的所有数据的和
    	sbuilder.addAggregation(totalFeeAgg).addAggregation(channelFeeAgg);
    	LOGGER.info("统计订单支付总金额和费率总金额查询语句------" + sbuilder.toString());
    	SearchResponse response = sbuilder.execute().actionGet();
    	Map<String, Aggregation> asMap = response.getAggregations().asMap();
    	
    	Map<String, Object> requestMap = new HashMap<String, Object>();
    	InternalSum totalFeeSum = (InternalSum) asMap.get("total_fee");
    	String totalCount = new BigDecimal(String.valueOf(totalFeeSum.getValue())).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    	requestMap.put("TOTAL_COUNT", totalCount);
    	InternalSum channlFeeSum = (InternalSum) asMap.get("channl_fee_cost");
    	String totalFee = new BigDecimal(String.valueOf(channlFeeSum.getValue())).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    	requestMap.put("TOTAL_FEE", totalFee);
		return requestMap;
	}
	
	/**
	 * 添加查询条件
	 * @param reqDTO
	 * @return
	 */
	public BoolQueryBuilder setESQueryParams(OrderQueryReqDTO reqDTO) {
		//查询条件BoolQueryBuilder类
    	BoolQueryBuilder queryBool = QueryBuilders.boolQuery();
    	
    	//payType
    	if (!StringUtils.isBlank(reqDTO.getPayType())) {
    		//支付渠道  match必须符合该条件
    		MatchQueryBuilder payTypeMatch = QueryBuilders.matchQuery("payType", reqDTO.getPayType());
    		queryBool.must(payTypeMatch);
    	}
    	
    	//status
    	if (reqDTO.getStatus() != null && reqDTO.getStatus() != 0L) {
    		//支付状态  match必须符合该条件
        	MatchQueryBuilder statusMatch = QueryBuilders.matchQuery("status", reqDTO.getStatus());
        	queryBool.must(statusMatch);
    	}
    	
    	//bporderid
    	if (!StringUtils.isBlank(reqDTO.getBpOrderId())) {
    		MatchQueryBuilder bpOrderIdMatch = QueryBuilders.matchQuery("bpOrderId", reqDTO.getBpOrderId());
    		queryBool.must(bpOrderIdMatch);
    	}
    	
    	//bpid
    	if (reqDTO.getBpId() != null && reqDTO.getBpId() != 0L) {
        	MatchQueryBuilder bpIdMatch = QueryBuilders.matchQuery("bpId", reqDTO.getBpId());
        	queryBool.must(bpIdMatch);
    	}
    	
    	//orderTradeNo
    	if (!StringUtils.isBlank(reqDTO.getOrderTradeNo())) {
    		MatchQueryBuilder orderTradeNoMatch = QueryBuilders.matchQuery("orderTradeNo", reqDTO.getOrderTradeNo());
    		queryBool.must(orderTradeNoMatch);
    	}
    	
    	//initOrderTerminal, 
    	if (!StringUtils.isBlank(reqDTO.getInitOrderTerminal())) {
    		MatchQueryBuilder orderTradeNoMatch = QueryBuilders.matchQuery("initOrderTerminal", reqDTO.getInitOrderTerminal());
    		queryBool.must(orderTradeNoMatch);
    	}
    	
    	//finalPayTerminal, 
    	if (!StringUtils.isBlank(reqDTO.getFinalPayTerminal())) {
    		MatchQueryBuilder finalPayTerminalMatch = QueryBuilders.matchQuery("finalPayTerminal", reqDTO.getFinalPayTerminal());
    		queryBool.must(finalPayTerminalMatch);
    	}
    	
    	//payBank, 
    	if (!StringUtils.isBlank(reqDTO.getPayBank())) {
    		MatchQueryBuilder payBankMatch = QueryBuilders.matchQuery("payBank", reqDTO.getPayBank());
    		queryBool.must(payBankMatch);
    	}
    	
    	//payService, 
    	if (!StringUtils.isBlank(reqDTO.getPayService())) {
    		MatchQueryBuilder payServiceMatch = QueryBuilders.matchQuery("payService", reqDTO.getPayService());
    		queryBool.must(payServiceMatch);
    	}
    	
    	//userName, 
    	if (!StringUtils.isBlank(reqDTO.getUserName())) {
    		MatchQueryBuilder userNameMatch = QueryBuilders.matchQuery("username", reqDTO.getUserName());
    		queryBool.must(userNameMatch);
    	}
    	//bpids
    	if (reqDTO.getBpIds() != null && reqDTO.getBpIds().size() > 0) {
    		//同payType   可以放一块   因为每个match中会分清楚查询的键是bpid还是payType
    		BoolQueryBuilder bpIdShould = QueryBuilders.boolQuery();
    		reqDTO.getBpIds().forEach(bpId -> {
    			bpIdShould.should(QueryBuilders.matchQuery("bpId", bpId));
    		});
    		queryBool.must(bpIdShould);
    	}
    	//range是范围查询 from开始 to结束
    	RangeQueryBuilder dateRang = QueryBuilders.rangeQuery("createDate").from(reqDTO.getStartTime()).to(reqDTO.getEndTime()).includeLower(true).includeUpper(true);
    	queryBool.must(dateRang);
    	return queryBool;
	}
}
