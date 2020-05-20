package com.acorus.store.common.util;

import com.acorus.store.common.result.Result;
import org.apache.commons.io.FilenameUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author CheneAntray
 * @create 2020-05-14 20:53
 */
public class FileOperatorUtil {

    @Value("${fileServer.url}")
    private static String fileUrl;

    public static String fileUpload(MultipartFile file) throws IOException, MyException {
        //原始名
        //file.getOriginalFilename()
        //文件本身
        //file.getBytes()
        //0:加载配置文件  IO 流 绝对路径
        String path = ClassUtils.getDefaultClassLoader()
                .getResource("tracker.conf").getPath();
        ClientGlobal.init(path);
        //1:连接跟踪器Tracker  ip port
        TrackerClient trackerClient = new TrackerClient();
        //2:连接    TrackerServer 保存着 存储节点的IP PORT  地址
        TrackerServer trackerServer = trackerClient.getConnection();
        //3:连接存储节点
        StorageClient1 storageClient1 = new StorageClient1(trackerServer,null);
        //扩展名
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        //4:上传文件
        String fileId = storageClient1.upload_file1(
                file.getBytes(), ext, null);
        //    group1/M00/00/00/wKhDyVsLn96APCdkAACGx2c4tJ4983.jpg
        System.out.println("http://192.168.200.128:8080/" + fileId);
        return fileUrl + fileId;
    }
}
