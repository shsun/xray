package org.ibase4j.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.utils.HttpServletRequestUtils;
import base.core.BaseController;
import org.ibase4j.core.support.HttpCode;
import org.ibase4j.core.support.decoder.BASE64Decoder;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.core.util.UploadUtil;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "文件上传接口", description = "文件上传接口")
@RequestMapping(value = "/upload", method = RequestMethod.POST)
public class UploadController extends BaseController {

    public String getService() {
        return null;
    }

    /**
     * 上传文件(支持批量)
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/image", method = { RequestMethod.GET, RequestMethod.POST })
    @ApiOperation(value = "上传图片")
    public Object uploadImage(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        List<String> fileNames = UploadUtil.uploadImage(request);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        return ab(fileNames, map);
    }

    /**
     * 上传文件(支持批量)
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/imageData", method = { RequestMethod.GET, RequestMethod.POST })
    @ApiOperation(value = "上传图片")
    public Object uploadImageData(HttpServletRequest request, HttpServletResponse response, ModelMap map) {

        Map tmpmap = HttpServletRequestUtils.getRequestMap(request);

        String[] fileDatas = request.getParameterValues("fileData");
        String tmp = request.getParameter("fileData");

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        List<String> fileNames = InstanceUtil.newArrayList();
        if (fileDatas != null) {
            for (int i = 0; i < fileDatas.length; i++) {
                String fileStr = fileDatas[i];
                if (fileStr != null && !"".equals(fileStr)) {
                    int index = fileStr.indexOf("base64");
                    if (index > 0) {
                        try {
                            String fileName = UUID.randomUUID().toString();
                            String preStr = fileStr.substring(0, index + 7);
                            String prefix = preStr.substring(preStr.indexOf("/") + 1, preStr.indexOf(";")).toLowerCase();
                            fileStr = fileStr.substring(fileStr.indexOf(",") + 1);
                            String pathDir = UploadUtil.getUploadDir(request);
                            BASE64Decoder decoder = new BASE64Decoder();
                            byte[] bb = decoder.decodeBuffer(fileStr);
                            for (int j = 0; j < bb.length; ++j) {
                                if (bb[j] < 0) {// 调整异常数据
                                    bb[j] += 256;
                                }
                            }
                            File dir = new File(pathDir);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            String distPath = pathDir + fileName + "." + prefix;
                            OutputStream out = new FileOutputStream(distPath);
                            out.write(bb);
                            out.flush();
                            out.close();
                            fileNames.add(fileName + "." + prefix);
                        } catch (Exception e) {
                            logger.error("上传文件异常：", e);
                        }
                    } else {
                        setModelMap(map, HttpCode.BAD_REQUEST);
                        map.put("msg", "请选择要上传的文件！");
                        return map;
                    }
                }
            }
        }
        return ab(fileNames, map);
    }

    private Object ab(List<String> fileNames, ModelMap map) {
        if (fileNames.size() > 0) {
            map.put("imgName", fileNames);
            return super.setSuccessModelMap(map);
        } else {
            super.setModelMap(map, HttpCode.BAD_REQUEST);
            map.put("msg", "请选择要上传的文件！");
            return map;
        }
    }
}
