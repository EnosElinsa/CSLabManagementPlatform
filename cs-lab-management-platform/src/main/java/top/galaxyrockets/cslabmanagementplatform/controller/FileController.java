package top.galaxyrockets.cslabmanagementplatform.controller;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import cn.hutool.core.io.FileUtil;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.galaxyrockets.cslabmanagementplatform.common.Result;
import top.galaxyrockets.cslabmanagementplatform.common.AuthAccess;


/**
 * @author EnosElinsa
 * @since 2024/04/14
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final String UPLOAD_PATH = System.getProperty("user.dir") + File.separator + "upload";
    
    @PostMapping("/upload")
    public Result upload(MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
        if (!FileUtil.exist(UPLOAD_PATH)) {
            FileUtil.mkdir(UPLOAD_PATH);
        } 
        String fileName = file.getOriginalFilename();
        if (FileUtil.exist(UPLOAD_PATH + File.separator + fileName)) {
            String mainName = FileUtil.mainName(fileName);
            String extName = FileUtil.extName(fileName);
            fileName = mainName + "_" + System.currentTimeMillis() + "." + extName;
        }
        file.transferTo(new File(UPLOAD_PATH + File.separator + fileName));

        String requestUrl = request.getRequestURL().toString();
        String downloadUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + "/download/" + fileName;

        return Result.success(downloadUrl);
    }

    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        // response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        String filePath = UPLOAD_PATH + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        var outputStream = response.getOutputStream();
        outputStream.write(FileUtil.readBytes(filePath));
        outputStream.flush();
        outputStream.close();
    }
    
}
