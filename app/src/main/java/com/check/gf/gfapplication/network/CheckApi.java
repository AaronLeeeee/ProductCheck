package com.check.gf.gfapplication.network;

import com.check.gf.gfapplication.entity.CheckOrder;
import com.check.gf.gfapplication.entity.CheckOrderInfo;
import com.check.gf.gfapplication.entity.InspectItem;
import com.check.gf.gfapplication.entity.InspectItemDetail;
import com.check.gf.gfapplication.entity.ResultObject;
import com.check.gf.gfapplication.entity.StartCheckResult;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Check Api
 *
 * @author nEdAy
 */
public interface CheckApi {

    /**
     * 检验单搜索接口
     *
     * @param customerName 否	string	客户名
     * @param requireDate  否	string	交期限
     * @param equipmentNo  否	string	设备号，也是检验单号
     * @param docNo        否	string	出货计划单
     * @param custNo       否	string	客户订单号
     * @return 检验单
     */
    @GET("Check/CheckOrderQuery")
    Observable<CheckOrder> CheckOrderQuery(@Query("customerName") String customerName, @Query("requireDate") String requireDate, @Query("equipmentNo") String equipmentNo, @Query("docNo") String docNo, @Query("custNo") String custNo);


    /**
     * 检验单基本信息查询
     *
     * @param equipmentNo 是		string	检验单号
     * @return 检验单详情
     */
    @GET("Check/CheckOrderInfoQuery")
    Observable<CheckOrderInfo> CheckOrderInfoQuery(@Query("equipmentNo") String equipmentNo);


//    /**
//     * 检验类别查询接口
//     *
//     * @return 检验类别
//     */
//    @GET("Check/InspectTypeQuery")
//    Observable<InspectType> InspectTypeQuery();


    /**
     * 检验条目查询接口
     *
     * @param inspectCode 是		string	检验类别编码
     * @param equipmentNo 是		string	检验单号
     * @return 检验条目信息
     */
    @GET("Check/InspectItemListQuery")
    Observable<InspectItem> InspectItemListQuery(@Query("inspectCode") String inspectCode, @Query("equipmentNo") String equipmentNo);


    /**
     * 开始检查接口
     *
     * @param equipmentNo 是		string	检验类别编码
     * @return 返回时间信息
     */
    @GET("Check/StartCheck")
    Observable<StartCheckResult> StartCheck(@Query("equipmentNo") String equipmentNo);


    /**
     * 更新检验结果
     *
     * @param equipmentNo 是 	string	检验单号
     * @param inspectCode 是	    string	检验类别编号
     * @param itemCode    是 	string	检验条目编码
     * @param checkResult 是	    int	    0:检验通过 1:检验失败
     * @param userName    是 	string	用户名
     * @param postCode    是 	string	用户工位编码
     * @param groupCode   是	    string	用户班组编码
     * @return 示例返回
     */
    @GET("Check/SaveCheckResult")
    Observable<ResultObject> SaveCheckResult(@Query("inspectCode") String inspectCode, @Query("equipmentNo") String equipmentNo, @Query("itemCode") String itemCode, @Query("checkResult") int checkResult, @Query("userName") String userName, @Query("postCode") String postCode, @Query("groupCode") String groupCode);


    /**
     * 提交检验备注信息
     *
     * @param equipmentNo  是	string	检验单号
     * @param inspectCode  是	string	检验类别编号
     * @param itemCode     是	string	检验条目编码
     * @param checkContent 是	string	检验备注信息
     * @return 示例返回
     */
    @GET("Check/SaveItemChkCnt")
    Observable<ResultObject> SaveItemChkCnt(@Query("equipmentNo") String equipmentNo, @Query("inspectCode") String inspectCode, @Query("itemCode") String itemCode, @Query("checkContent") String checkContent);


    /**
     * 上传检验条目图片
     *
     * @param equipmentNo 是	string	检验单号
     * @param inspectCode 是	string	检验类别编号
     * @param itemCode    是	string	检验条目编号
     * @param file        是	数据流	图片数据流
     * @return 示例返回
     */
    @Multipart
    @POST("Check/ItemChkUploadImg")
    Observable<ResultObject> ItemChkUploadImg(@Query("equipmentNo") String equipmentNo, @Query("inspectCode") String inspectCode, @Query("itemCode") String itemCode, @Part MultipartBody.Part file);


//    /**
//     * 检验条目图片查询接口
//     *
//     * @param inspectCode 是	string	检验类别编号
//     * @param equipmentNo 是	string	检验单号
//     * @param itemCode    是	string	检验条目编码
//     * @return 示例返回
//     */
//    @GET("Check/ItemPicListQuery")
//    Observable<ItemPic> ItemPicListQuery(@Query("inspectCode") String inspectCode, @Query("equipmentNo") String equipmentNo, @Query("itemCode") String itemCode);


    /**
     * 检验条目图片查询接口
     *
     * @param inspectCode 是	string	检验类别编号
     * @param equipmentNo 是	string	检验单号
     * @param itemCode    是	string	检验条目编码
     * @return 示例返回
     */
    @GET("Check/ItemDetailQuery")
    Observable<InspectItemDetail> ItemDetailQuery(@Query("inspectCode") String inspectCode, @Query("equipmentNo") String equipmentNo, @Query("itemCode") String itemCode);

}
