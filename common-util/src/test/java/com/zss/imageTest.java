package com.zss;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/21 16:59<br/>
 *
 * @author 16574<br />
 */
public class imageTest {
    @Test
    public void testDecode(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());

//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "KL40M2h2A9ZbjHWjdqpc1uh8ydkJ_jYtqDAQT5NJ";
        String secretKey = "3tnGjqTpgHrY5BiJWf5JgL7NtlUVXgNB-TAVXyzO";
        String bucket = "shf-zss";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:/Users/16574/Pictures/idea背景图片/3.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}
