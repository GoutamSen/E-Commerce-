package com.dollop.app.services;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

	
	String uploadFile(MultipartFile file,String path) throws IOException;
	InputStream getResourse(String path,List<String> list) throws FileNotFoundException;
	
	//List<String> uploadFile(List<MultipartFile> files) throws IOException;
}
