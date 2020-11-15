//package com.arc.zero.controller.data.file;
//
//import com.arc.zero.config.properties.file.FileProperties;
//import com.arc.zero.service.file.MusicFileService;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.annotation.Resource;
//
///**
// * @author yechao
// * @date 2020/8/26 6:05 下午
// */
//@RequestMapping({"music"})
//public class MusicController {
//
//    /**
//     * 文件持久化等相关参数配置
//     */
//    @Resource
//    private FileProperties fileProperties;
//
//
//    @Resource
//    private MusicFileService musicFileService;


//    //####################################### 文件上传 & 下载 ###################################

//    /**
//     * 单文件上传
//     * 记录日志
//     * 判合法性，非空，大小，格式
//     * 1、文件写入磁盘,注意文件不会被覆盖，因为不存在同名文件
//     * 2、描述信息记录数据库
//     *
//     * @param file MultipartFile
//     * @return flag=这里是返回文件在磁盘的路径，便于测试
//     */
//    //上传文件的问题  持久化操作出错，记录时候出错
//    //正常情况：变量名称，文件大小，文件格式，文件名称重复时候处理，文件在磁盘上的路径处理，文件路径记录到数据库，生成唯一编号
//    //异常情况：文件为空判断，路径为空或者不合法判断，不支持的文件类型判断，文件重复判断，写入磁盘异常判断，写入磁盘成功，但是信息记录数据库出错，造成数据耗着磁盘，但是应用检索数据库时候并无法发现该文件， 需要做补偿来删除脏数据
//    @PostMapping("/upload")
//    @Note("单个文件上传")
//    public ResponseEntity singleFileUpload(MultipartFile file) {
//        //需求判断文件是否为空 大小已经在yml中做了配置
//        if (file == null || file.isEmpty()) {
//            log.info(ProjectCodeEnum.UPLOAD_FAILURE.getMsg());
//            return ResponseEntity.ok(ResponseVo.success(ProjectCodeEnum.UPLOAD_FAILURE));
//        }
//        log.debug("文件上传入参: 类型={}，名称={}，尺寸={} bytes", file.getContentType(), file.getOriginalFilename(), file.getSize());
//        String flag = fileService.writeFileToDiskAndRecord(file, fileProperties.getFilePersistenceDirectory());
//        return flag == null ? ResponseEntity.status(500).body(ResponseVo.failure(ProjectCodeEnum.UPLOAD_FAILURE)) : ResponseEntity.ok(ResponseVo.success(flag));
//    }
//
//
//    //todo 测试两种方法是否有效
//    @RequestMapping(value = "/delete/{code}", method = {RequestMethod.DELETE, RequestMethod.GET})
//    public ResponseVo deleteByIdOrCode(@PathVariable String code) {
//        return ResponseVo.success(musicFileService.deleteByIdOrCode(code));
//    }
//
//
////    @GetMapping("/rename/change/profile")
////    public ResponseEntity rename() {
////        List<SysFile> list = sysFileService.list();
////        return ResponseEntity.ok(list);
////    }
//
//    @PostMapping("/init")
//    public ResponseEntity init(@RequestBody Map<String, Object> map) {
//        //文件扫描后入库 返回成功的数量
//        Integer success = musicFileService.init(map);
//        return ResponseEntity.ok(success);
//    }
//
//
//    @PostMapping("/init/2")
//    public ResponseEntity init2(String path) {
//        //文件扫描后入库 返回成功的数量
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("path", path);
//        Integer success = musicFileService.init(map);
//        return ResponseEntity.ok(success);
//    }
//}
