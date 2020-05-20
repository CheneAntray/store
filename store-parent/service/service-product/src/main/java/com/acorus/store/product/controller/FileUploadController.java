package com.acorus.store.product.controller;

import com.acorus.store.common.result.Result;
import com.acorus.store.common.util.FileOperatorUtil;
import io.swagger.annotations.Api;
import org.csource.common.MyException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author CheneAntray
 * @create 2020-05-14 21:16
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping("/admin/product")
//@CrossOrigin
public class FileUploadController {

    @RequestMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws IOException, MyException {
        System.out.println(FileOperatorUtil.fileUpload(file));
        return Result.ok(FileOperatorUtil.fileUpload(file));
    }

}
