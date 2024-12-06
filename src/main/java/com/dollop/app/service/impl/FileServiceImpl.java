package com.dollop.app.service.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.dollop.app.dtos.BadApiRequestException;
import com.dollop.app.services.IFileService;

@Service
public class FileServiceImpl implements IFileService {

	private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		String originalFilename=file.getOriginalFilename();        //read originalFileName
		logger.info("File Name : {}",originalFilename);
		String filename=UUID.randomUUID().toString();              //for unique name
		logger.info("filename : {} ",filename);
		String extension=originalFilename.substring(originalFilename.lastIndexOf("."));  //extension
		String fileNameWithExtension=filename+extension;           //extension
		String fullPathWithFileName=path+fileNameWithExtension;
		logger.info("Full Image Path : {}",fullPathWithFileName);
		if(extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".jpeg")) {
			logger.info("Extension is : {}",extension);
			File folder=new File(path);
			if(!folder.exists()) {
				folder.mkdirs();
			}
			Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
		}
		else 
			throw new  BadApiRequestException("File With this "+extension+" not allowed");
		return fileNameWithExtension;
	}

	@Override
	public InputStream getResourse(String path, List<String> list) throws FileNotFoundException {
      String fullPath=path+File.separator+list;
      InputStream inputStream=new FileInputStream(fullPath);
		return inputStream;
	}
	
}
