package com.check.gf.gfapplication.network;

import com.check.gf.gfapplication.entity.CheckOrder;
import com.check.gf.gfapplication.entity.CheckOrderInfo;
import com.check.gf.gfapplication.entity.FinishCheckResult;
import com.check.gf.gfapplication.entity.ImageUploadResult;
import com.check.gf.gfapplication.entity.InspectItem;
import com.check.gf.gfapplication.entity.InspectItemDetail;
import com.check.gf.gfapplication.entity.ResultObject;
import com.check.gf.gfapplication.entity.StartCheckResult;

import java.util.List;

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
     * @param materialCode 否	string	物料型号
     * @param custNo       否	string	客户订单号
     * @return 检验单
     */
    @GET("Check/CheckOrderQuery")
    Observable<ResultObject<List<CheckOrder>>> CheckOrderQuery(@Query("customerName") String customerName, @Query("requireDate") String requireDate, @Query("equipmentNo") String equipmentNo, @Query("docNo") String docNo, @Query("materialCode") String materialCode, @Query("custNo") String custNo);


    /**
     * 检验单基本信息查询
     *
     * @param equipmentNo       是		string	检验单号
     * @param materialCode      否	string	物料型号
     * @param equipmentNoSecond 否	string	次要检验单号，从筛选那里过来为空
     * @return 检验单详情
     */
    @GET("Check/CheckOrderInfoQuery")
    Observable<ResultObject<CheckOrderInfo>> CheckOrderInfoQuery(@Query("equipmentNo") String equipmentNo, @Query("materialCode") String materialCode, @Query("equipmentNoSecond") String equipmentNoSecond);


    /**
     * 检验条目查询接口
     *
     * @param inspectCode       是		string	检验类别编码
     * @param equipmentNo       是		string	检验单号
     * @param equipmentNoSecond 是	string	次要检验单号
     * @return 检验条目信息
     */
    @GET("Check/InspectItemListQuery")
    Observable<ResultObject<List<InspectItem>>> InspectItemListQuery(@Query("inspectCode") String inspectCode, @Query("equipmentNo") String equipmentNo, @Query("equipmentNoSecond") String equipmentNoSecond);


    /**
     * 开始检查接口
     *
     * @param equipmentNo       是		string	检验类别编码
     * @param materialCode      否	string	物料型号
     * @param equipmentNoSecond 是	string	次要检验单号
     * @param userRealName      是	string	登录用户名字，中文名
     * @return 返回时间信息
     */
    @GET("Check/StartCheck")
    Observable<ResultObject<StartCheckResult>> StartCheck(@Query("equipmentNo") String equipmentNo, @Query("materialCode") String materialCode, @Query("equipmentNoSecond") String equipmentNoSecond, @Query("userRealName") String userRealName);


    /**
     * 更新检验结果
     *
     * @param equipmentNo       是 	string	检验单号
     * @param inspectCode       是	    string	检验类别编号
     * @param materialCode      否	string	物料型号
     * @param itemCode          是 	string	检验条目编码
     * @param checkResult       是	    int	    0:检验通过 1:检验失败
     * @param userName          是 	string	用户名
     * @param postCode          是 	string	用户工位编码
     * @param groupCode         是	    string	用户班组编码
     * @param userRealName      是	    string		登录用户中文名
     * @param equipmentNoSecond 是	    string		次要检验单号
     * @return 示例返回
     */
    @GET("Check/SaveCheckResult")
    Observable<ResultObject> SaveCheckResult(@Query("inspectCode") String inspectCode, @Query("equipmentNo") String equipmentNo, @Query("materialCode") String materialCode, @Query("itemCode") String itemCode, @Query("checkResult") int checkResult, @Query("userName") String userName, @Query("postCode") String postCode, @Query("groupCode") String groupCode, @Query("userRealName") String userRealName, @Query("equipmentNoSecond") String equipmentNoSecond);


    /**
     * 提交检验备注信息
     *
     * @param equipmentNo       是	string	检验单号
     * @param materialCode      否	string	物料型号
     * @param inspectCode       是	string	检验类别编号
     * @param itemCode          是	string	检验条目编码
     * @param checkContent1     是	string	检验备注信息1
     * @param checkContent2     是	string	检验备注信息2
     * @param checkContent3     是	string	检验备注信息3
     * @param checkContent4     是	string	检验备注信息4
     * @param checkContent5     是	string	检验备注信息5
     * @param userRealName      是	    string		登录用户中文名
     * @param equipmentNoSecond 是	    string		次要检验单号
     * @return 示例返回
     */
    @GET("Check/SaveItemChkCnt")
    Observable<ResultObject> SaveItemChkCnt(@Query("equipmentNo") String equipmentNo, @Query("materialCode") String materialCode, @Query("inspectCode") String inspectCode, @Query("itemCode") String itemCode, @Query("checkContent1") String checkContent1, @Query("checkContent2") String checkContent2, @Query("checkContent3") String checkContent3, @Query("checkContent4") String checkContent4, @Query("checkContent5") String checkContent5, @Query("userRealName") String userRealName, @Query("equipmentNoSecond") String equipmentNoSecond);


    /**
     * 上传检验条目图片
     *
     * @param equipmentNo       是	string	检验单号
     * @param materialCode      否	string	物料型号
     * @param inspectCode       是	string	检验类别编号
     * @param itemCode          是	string	检验条目编号
     * @param file              是	数据流	图片数据流
     * @param userRealName      是	    string		登录用户中文名
     * @param equipmentNoSecond 是	    string		次要检验单号
     * @return 示例返回
     */
    @Multipart
    @POST("Check/ItemChkUploadImg")
    Observable<ImageUploadResult> ItemChkUploadImg(@Query("equipmentNo") String equipmentNo, @Query("materialCode") String materialCode, @Query("inspectCode") String inspectCode, @Query("itemCode") String itemCode, @Part MultipartBody.Part file, @Query("userRealName") String userRealName, @Query("equipmentNoSecond") String equipmentNoSecond);

    /**
     * 检验条目图片查询接口
     *
     * @param inspectCode       是	string	检验类别编号
     * @param equipmentNo       是	string	检验单号
     * @param materialCode      否	string	物料型号
     * @param itemCode          是	string	检验条目编码
     * @param userRealName      是	    string		登录用户中文名
     * @param equipmentNoSecond 是	    string		次要检验单号
     * @return 示例返回
     */
    @GET("Check/ItemDetailQuery")
    Observable<ResultObject<InspectItemDetail>> ItemDetailQuery(@Query("inspectCode") String inspectCode, @Query("equipmentNo") String equipmentNo, @Query("materialCode") String materialCode, @Query("itemCode") String itemCode, @Query("userRealName") String userRealName, @Query("equipmentNoSecond") String equipmentNoSecond);

    /**
     * 开始检查接口
     *
     * @param equipmentNo       是		string	检验类别编码
     * @param equipmentNoSecond 是	string	次要检验单号
     * @param userRealName      是	string	登录用户名字，中文名
     * @return 返回时间信息
     */
    @GET("Check/FinishCheck")
    Observable<ResultObject<FinishCheckResult>> FinishCheck(@Query("equipmentNo") String equipmentNo, @Query("equipmentNoSecond") String equipmentNoSecond, @Query("userRealName") String userRealName);


    /**
     * 查询设备是否检查接口
     *
     * @param equipmentNo       是		string	检验类别编码
     * @param equipmentNoSecond 是	string	次要检验单号
     * @return 返回时间信息
     */
    @GET("Check/QueryEquipmentNoCheck")
    Observable<ResultObject> QueryEquipmentNoCheck(@Query("equipmentNo") String equipmentNo, @Query("equipmentNoSecond") String equipmentNoSecond);

}
