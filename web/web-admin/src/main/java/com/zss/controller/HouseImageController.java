package com.zss.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zss.entity.HouseImage;
import com.zss.result.Result;
import com.zss.service.HouseImageService;
import com.zss.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2022/10/21 17:30<br/>
 *
 * @author 16574<br />
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController extends  BaseController<HouseImage>{

    @Reference
    private HouseImageService houseImageService;

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String toUpload(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, ModelMap modelMap) {
        modelMap.addAttribute("houseId", houseId);
        modelMap.addAttribute("type", type);
        return "house/upload";
    }

    @RequestMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type, @RequestParam("file") MultipartFile[] files)  {

                try {
                    if (files != null && files.length > 0) {
                        for (MultipartFile file : files) {
                            String newFileName = UUID.randomUUID().toString();
                            QiniuUtil.upload2Qiniu(file.getBytes(), newFileName);
                            String url = "http://zhangss.cloud/" + newFileName;
                            HouseImage houseImage = new HouseImage();
                            houseImage.setHouseId(houseId);
                            houseImage.setType(type);
                            houseImage.setImageUrl(url);
                            houseImage.setImageName(newFileName);
                            houseImageService.insert(houseImage);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        return Result.ok();
    }
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId, @PathVariable("id") Long id){
        houseImageService.delete(id);
        return "redirect:/house/" +houseId;
    }
}