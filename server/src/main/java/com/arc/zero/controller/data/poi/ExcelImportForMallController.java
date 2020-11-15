package com.arc.zero.controller.data.poi;

import com.arc.core.config.annotations.Note;
import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.exception.BizException;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.mall.MallService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.arc.core.enums.system.ProjectCodeEnum.ILLEGAL_FILE;

/**
 * 测试为合并的多个单元格赋值，并为合并的单元格设置样式
 *
 * @author yechao
 * @date 2019/1/9 11:41
 */
@Slf4j
@RestController
@RequestMapping("/v1/excel")
public class ExcelImportForMallController {

    private static final String UPLOAD_DIRECTORY = File.pathSeparator;

    //https://blog.csdn.net/u014746965/article/details/78772896

    @Autowired
    private MallService mallService;

    /**
     * 注意：同一行上的只能创建同一个行对象row  否则赋值的内容会被覆盖。
     */
    @Note
    @GetMapping("/import")
    public ResponseVo v1(@RequestParam Long id) {
        return ResponseVo.success(mallService.importExcel(id));
    }


    /**
     * 多个文件上传
     *
     * @param files
     * @return
     */
    @RequestMapping(value = "uploadFiles", method = RequestMethod.POST)
    public ModelAndView uploadFile(@RequestParam("files") MultipartFile[] files) {
        //判断file数组不能为空并且长度大于0
        if (files != null && files.length > 0) {
            //循环获取file数组中得文件
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];

                //保存文件
                String filePath = FilenameUtils.concat(UPLOAD_DIRECTORY, file.getOriginalFilename());
                // 转存文件
                try {
                    file.transferTo(new File(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //跳转视图
        return new ModelAndView("uploadSuccess", "msg", files.length + "个文件");
    }


    @RequestMapping(value = "/test/1", method = RequestMethod.GET)
    public ResponseVo export2(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> excelEntityList = handleMultipartFile(multipartFile);
        return ResponseVo.success(excelEntityList);
    }

    private List<Map<String, Object>> handleMultipartFile(MultipartFile multipartFile) {

        //校验文件非空
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new BizException(ProjectCodeEnum.FILE_OPERATE_ERROR);
        }

        //multipartFile to File  then analysis

        //校验文件名称
        String originalFilename = multipartFile.getOriginalFilename();
        int indexOfLastPoint = originalFilename.lastIndexOf(".");
        if (indexOfLastPoint == -1) {
            log.warn(ILLEGAL_FILE.getMsg() + "error 非法文件={},size={}，ContentType={}", originalFilename, multipartFile.getSize(), multipartFile.getContentType());
            throw new BizException(ILLEGAL_FILE);
        }

        //获取文件 File
        File excelFile = null;
        // 获取文件名
        // 获取文件前缀与后缀
        String prefix = originalFilename.substring(0, indexOfLastPoint);
        String suffix = originalFilename.substring(indexOfLastPoint);
        // 用uuid作为文件名，防止生成的临时文件重复
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            excelFile = File.createTempFile(prefix + uuid, suffix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 转存文件 MultipartFile to File
        try {
            multipartFile.transferTo(excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //真正处理文件的逻辑
        List<Map<String, Object>> resultList = analysisExcelFile(excelFile);
        return resultList;
    }

    private List<Map<String, Object>> analysisExcelFile(File excelFile) {

        System.out.println(excelFile.getPath());
        System.out.println(excelFile.getName());
        System.out.println(excelFile.getParent());
        System.out.println(excelFile.getAbsoluteFile());
        deleteFile(excelFile);
        return null;
    }

    /**
     * 删除
     *
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                try {
                    boolean delete = file.delete();
                } catch (Exception e) {
                    log.error("ERROR 文件删除时候出错={}，filePath={}", e, file.getPath());
                }
            }
        }
    }


    public static void main(String[] args) {
        int num = 2;
        int total = 100;
        int ret = num / total;
        float fRet = (float) num / total;
        System.out.println(ret);
        System.out.println(fRet);
        System.out.println("---------------");


        int actualCompletedNumber = 10;
        int targetTotalNumber = 10000;
        int degreeOfCompletion = 0;

        System.out.println(degreeOfCompletion);
        if (targetTotalNumber != 0) {
            fRet = (float) actualCompletedNumber / targetTotalNumber;
            System.out.println(String.valueOf(fRet));
            degreeOfCompletion = (int) (fRet * 10000);
            System.out.println(degreeOfCompletion);
        }
    }
}
