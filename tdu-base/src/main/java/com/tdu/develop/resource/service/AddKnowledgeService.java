package com.tdu.develop.resource.service;


import com.tdu.develop.resource.pojo.Knowlegcontent;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface AddKnowledgeService {

	

	String addUploading(Knowlegcontent knowlegcontent, String contentType, String fileName, String treeId, String clickNodeId, MultipartFile file)throws IOException;
	
	Knowlegcontent getKnowlegContentName(String id);
	
	String isKnowledgeContent(String nodeId);

	String addSimulateModel(Knowlegcontent knowledgecontent, String treeId, String nodeId);

	String addQuesModel(Knowlegcontent knowlegcontent, String treeId, String nodeId);

	String addCustomModel(Knowlegcontent knowlegcontent, String treeId, String nodeId);
	

	String addHtmlEditorContent(Knowlegcontent knowlegcontent, String htmlcontent, String treeId, String nodeId);

	String getKnowIdbycontentId(String contentId);
	
	/**
	 * pdf编辑功能
	 * @param knowlegcontent
	 * @param contentType
	 * @param fileName
	 * @param treeId
	 * @param clickNodeId
	 * @param file
	 * @return
	 * @throws IOException
	 */
	String editorLoading(Knowlegcontent knowlegcontent, String contentType, String fileName, String treeId, String clickNodeId, MultipartFile file)throws IOException;
	
	/**
	 * 
	 * @param mf
	 * @param subjectId
	 * @param treeId
	 * @param filePath
	 * @param knowlegcontent
	 * @throws IOException
	 */
	public void updatePdf(MultipartFile mf, String subjectId, String treeId, String filePath, Knowlegcontent knowlegcontent, String clickNodeId) throws IOException ;
	/**
	 * 添加考试
	 * @param knowlegcontent
	 * @param treeId
	 * @param nodeId
	 * @return
	 */
	public String addExamModel(Knowlegcontent knowlegcontent, String treeId, String nodeId);
	/**
	 * 获取考试名称
	 * @param examId
	 * @return
	 */
	public Knowlegcontent getExamName(String examId);
}
