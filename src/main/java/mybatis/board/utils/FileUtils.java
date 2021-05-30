package mybatis.board.utils;

import lombok.extern.slf4j.Slf4j;
import mybatis.board.domain.user.UserVO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.*;
import java.io.File;

@Slf4j
@Component("fileUtils")
public class FileUtils {
    private static  final String filePath= "C:\\mp\\file\\";

    public List<Map<String,Object>> parseInsertFileInfo(UserVO userVO,
                                                        MultipartHttpServletRequest mpRequest)throws Exception{

        Iterator<String> iterator = mpRequest.getFileNames();
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> listMap = null;

        long id = userVO.getId();

        File file = new File(filePath);
        if (file.exists() == false){
            file.mkdir();
        }

        while(iterator.hasNext())
        {
            multipartFile = mpRequest.getFile(iterator.next());
            if(multipartFile.isEmpty() == false)
            {
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = getRandomString() + originalFileExtension;

                file = new File(filePath + storedFileName);
                multipartFile.transferTo(file);
                listMap = new HashMap<String,Object>();
                listMap.put("id",id);
                log.debug("FileUtilsId={}",listMap.get("id"));
                listMap.put("ORG_FILE_NAME",originalFileName);
                log.debug("ORG_FILE_NAME={}",listMap.get("ORG_FILE_NAME"));
                listMap.put("STORED_FILE_NAME",storedFileName);
                log.debug("STORED_FILE_NAME={}",listMap.get("STORED_FILE_NAME"));
                listMap.put("FILE_SIZE",multipartFile.getSize());
                log.debug("FILE_SIZE={}",listMap.get("FILE_SIZE"));

                list.add(listMap);

            }
        }
        return list;
    }
    public static String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
