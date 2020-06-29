package com.tdu.develop.resource.controller;

import com.tdu.develop.common.exception.JsonResult;
import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.resource.pojo.ZNodes;
import com.tdu.develop.resource.service.KnowledgesService;
import com.tdu.develop.resource.service.impl.AddKnowledgeServiceImpl;
import com.tdu.develop.resource.service.impl.KnowledgesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-14-14:32
 */
@CrossOrigin
@Controller
@RequestMapping(value="TreesController")
public class TreesController {
	@Autowired
	KnowledgesServiceImpl knowledgesServiceImp;
	@Autowired
	KnowledgesService ks;
	

	@Autowired
	AddKnowledgeServiceImpl adksi;
////	//主页声音目录的新增
////	@RequestMapping(value="insVoices.action",method={RequestMethod.POST})
////	@ResponseBody
////	public ZNodes insVoices(HttpServletRequest request, HttpServletResponse response, HttpSession session){
////		Voices voices=new Voices();
////		String userId = session.getAttribute("ID").toString();
////		voices.setUserKey(userId);
////		String id=UUID.randomUUID().toString();
////		String content= request.getParameter("Content");
////		if(content==null||content=="") {
////			content="新建目录";
////		}
////		//科目树id
////		String subjectTree_Id=request.getParameter("subid");
////		String imageIcons="../../../Source/imgicon/tag_orange.png";
////		String knowledgecontentId="00000000-0000-0000-0000-000000000000";
////		String beforCondition="<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
////		//上一节点id
////		String preknowledge=request.getParameter("lei");
////		/*if(preknowledge==null||"".equals(preknowledge))
////			throw new ServiceException("新增失败");*/
////		//添加子科目目录
////		if (request.getParameter("id")!="") {
////			//父节点id
////			String parentKnowledge=request.getParameter("id");
////
////			voices.setId(id);
////			voices.setContent(content);
////			voices.setParentVoice(parentKnowledge);
////			if (!preknowledge.isEmpty()) {
////				voices.setPreVoice(preknowledge);
////			}
////			voices.setSubjectTree_Id(subjectTree_Id);
////			voices.setImageIcons(imageIcons);
////			voices.setVoiceContentId(knowledgecontentId);
////			voices.setBeforCondition(beforCondition);
////			//向knowledge表新增数据
////			Boolean i=knowledgesServiceImp.inknowVoices(voices);
////				if (i==true) {
////					ZNodes zNodes=new ZNodes();
////					zNodes.setId(voices.getId());
////					zNodes.setpId(voices.getParentVoice());
////					zNodes.setName(voices.getContent());
////					zNodes.setKnowledgecontentId(voices.getVoiceContentId());
////					return zNodes;
////				}
////		}
//		//添加科目目录
//		else if (request.getParameter("id")=="") {
//			//获取ROOT的ID
//			String parentKnowledge=knowledgesServiceImp.slRootVoices(subjectTree_Id);
//			voices.setId(id);
//			voices.setContent(content);
//			voices.setParentVoice(parentKnowledge);
//			if (!preknowledge.isEmpty()) {
//				voices.setPreVoice(preknowledge);
//			}
//			voices.setSubjectTree_Id(subjectTree_Id);
//			voices.setImageIcons(imageIcons);
//			voices.setVoiceContentId(knowledgecontentId);
//			voices.setBeforCondition(beforCondition);
//
//			Boolean i=knowledgesServiceImp.inknowVoices(voices);
//
//			/*增加模型分类*/
//			ModelContact modelContact =new ModelContact();
//			modelContact.setId(id);
//			modelContact.setName(content);
//			modelContact.setParentId("0aec2588-4f01-4a94-b27a-fb2e20c8c85b");
//			mlsi.addModelContact(modelContact);
//			/*end*/
//
//			if (i==true) {
//				ZNodes zNodes=new ZNodes();
//				zNodes.setId(voices.getId());
//				zNodes.setpId("0");
//				zNodes.setName(voices.getContent());
//				zNodes.setKnowledgecontentId(voices.getVoiceContentId());
//				return zNodes;
//			}
//		}
//
//			return null;
//	}
//	//主页贴图目录的新增
//			@RequestMapping(value="insChartlets.action",method={RequestMethod.POST})
//			@ResponseBody
//			public ZNodes insChartlets(HttpServletRequest request,HttpServletResponse response,HttpSession session){
//				Chartlets chartlets=new Chartlets();
//				String userId = session.getAttribute("ID").toString();
//				chartlets.setUserKey(userId);
//				String id=UUID.randomUUID().toString();
//				String content= request.getParameter("Content");
//				if(content==null||content=="") {
//					content="新建目录";
//				}
//				//科目树id
//				String subjectTree_Id=request.getParameter("subid");
//				String imageIcons="../../../Source/imgicon/tag_orange.png";
//				String knowledgecontentId="00000000-0000-0000-0000-000000000000";
//				String beforCondition="<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
//				//上一节点id
//				String preknowledge=request.getParameter("lei");
//				/*if(preknowledge==null||"".equals(preknowledge))
//					throw new ServiceException("新增失败");*/
//				//添加子科目目录
//				if (request.getParameter("id")!="") {
//					//父节点id
//					String parentKnowledge=request.getParameter("id");
//
//					chartlets.setId(id);
//					chartlets.setContent(content);
//					chartlets.setParentChartlet(parentKnowledge);
//					if (!preknowledge.isEmpty()) {
//						chartlets.setPreChartlet(preknowledge);
//					}
//					chartlets.setSubjectTree_Id(subjectTree_Id);
//					chartlets.setImageIcons(imageIcons);
//					chartlets.setChartletContentId(knowledgecontentId);
//					chartlets.setBeforCondition(beforCondition);
//					//向knowledge表新增数据
//					Boolean i=knowledgesServiceImp.inknowChartlets(chartlets);
//						if (i==true) {
//							ZNodes zNodes=new ZNodes();
//							zNodes.setId(chartlets.getId());
//							zNodes.setpId(chartlets.getParentChartlet());
//							zNodes.setName(chartlets.getContent());
//							zNodes.setKnowledgecontentId(chartlets.getChartletContentId());
//							return zNodes;
//						}
//				}
//				//添加科目目录
//				else if (request.getParameter("id")=="") {
//					//获取ROOT的ID
//					String parentKnowledge=knowledgesServiceImp.slRootChartlets(subjectTree_Id);
//					chartlets.setId(id);
//					chartlets.setContent(content);
//					chartlets.setParentChartlet(parentKnowledge);
//					if (!preknowledge.isEmpty()) {
//						chartlets.setPreChartlet(preknowledge);
//					}
//					chartlets.setSubjectTree_Id(subjectTree_Id);
//					chartlets.setImageIcons(imageIcons);
//					chartlets.setChartletContentId(knowledgecontentId);
//					chartlets.setBeforCondition(beforCondition);
//
//					Boolean i=knowledgesServiceImp.inknowChartlets(chartlets);
//
//					/*增加模型分类*/
//					ModelContact modelContact =new ModelContact();
//					modelContact.setId(id);
//					modelContact.setName(content);
//					modelContact.setParentId("0aec2588-4f01-4a94-b27a-fb2e20c8c85b");
//					mlsi.addModelContact(modelContact);
//					/*end*/
//
//					if (i==true) {
//						ZNodes zNodes=new ZNodes();
//						zNodes.setId(chartlets.getId());
//						zNodes.setpId("0");
//						zNodes.setName(chartlets.getContent());
//						zNodes.setKnowledgecontentId(chartlets.getChartletContentId());
//						return zNodes;
//					}
//				}
//
//					return null;
//			}
//	//主页场景目录的新增
//			@RequestMapping(value="insScenes.action",method={RequestMethod.POST})
//			@ResponseBody
//			public ZNodes insScenes(HttpServletRequest request,HttpServletResponse response,HttpSession session){
//				Scenes scenes=new Scenes();
//				String userId = session.getAttribute("ID").toString();
//				scenes.setUserKey(userId);
//				String id=UUID.randomUUID().toString();
//				String content= request.getParameter("Content");
//				if(content==null||content=="") {
//					content="新建目录";
//				}
//				//科目树id
//				String subjectTree_Id=request.getParameter("subid");
//				String imageIcons="../../../Source/imgicon/tag_orange.png";
//				String knowledgecontentId="00000000-0000-0000-0000-000000000000";
//				String beforCondition="<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
//				//上一节点id
//				String preknowledge=request.getParameter("lei");
//				/*if(preknowledge==null||"".equals(preknowledge))
//					throw new ServiceException("新增失败");*/
//				//添加子科目目录
//				if (request.getParameter("id")!="") {
//					//父节点id
//					String parentKnowledge=request.getParameter("id");
//
//					scenes.setId(id);
//					scenes.setContent(content);
//					scenes.setParentScene(parentKnowledge);
//					if (!preknowledge.isEmpty()) {
//						scenes.setPreScene(preknowledge);
//					}
//					scenes.setSubjectTree_Id(subjectTree_Id);
//					scenes.setImageIcons(imageIcons);
//					scenes.setSceneContentId(knowledgecontentId);
//					scenes.setBeforCondition(beforCondition);
//					//向knowledge表新增数据
//					Boolean i=knowledgesServiceImp.inknowScenes(scenes);
//						if (i==true) {
//							ZNodes zNodes=new ZNodes();
//							zNodes.setId(scenes.getId());
//							zNodes.setpId(scenes.getParentScene());
//							zNodes.setName(scenes.getContent());
//							zNodes.setKnowledgecontentId(scenes.getSceneContentId());
//							return zNodes;
//						}
//				}
//				//添加科目目录
//				else if (request.getParameter("id")=="") {
//					//获取ROOT的ID
//					String parentKnowledge=knowledgesServiceImp.slRootScenes(subjectTree_Id);
//					scenes.setId(id);
//					scenes.setContent(content);
//					scenes.setParentScene(parentKnowledge);
//					if (!preknowledge.isEmpty()) {
//						scenes.setPreScene(preknowledge);
//					}
//					scenes.setSubjectTree_Id(subjectTree_Id);
//					scenes.setImageIcons(imageIcons);
//					scenes.setSceneContentId(knowledgecontentId);
//					scenes.setBeforCondition(beforCondition);
//
//					Boolean i=knowledgesServiceImp.inknowScenes(scenes);
//
//					/*增加模型分类*/
//					ModelContact modelContact =new ModelContact();
//					modelContact.setId(id);
//					modelContact.setName(content);
//					modelContact.setParentId("7fb53e8f-baf1-4c1e-8868-bad634e81461");
//					mlsi.addModelContact(modelContact);
//					/*end*/
//
//					if (i==true) {
//						ZNodes zNodes=new ZNodes();
//						zNodes.setId(scenes.getId());
//						zNodes.setpId("0");
//						zNodes.setName(scenes.getContent());
//						zNodes.setKnowledgecontentId(scenes.getSceneContentId());
//						return zNodes;
//					}
//				}
//
//					return null;
//			}
//	//主页模型目录的新增
//		@RequestMapping(value="insModels.action",method={RequestMethod.POST})
//		@ResponseBody
//		public ZNodes insModels(HttpServletRequest request,HttpServletResponse response,HttpSession session){
//			Models models=new Models();
//			String userId = session.getAttribute("ID").toString();
//			models.setUserKey(userId);
//			String id=UUID.randomUUID().toString();
//			String content= request.getParameter("Content");
//			if(content==null||content=="") {
//				content="新建目录";
//			}
//			//科目树id
//			String subjectTree_Id=request.getParameter("subid");
//			String imageIcons="../../../Source/imgicon/tag_orange.png";
//			String knowledgecontentId="00000000-0000-0000-0000-000000000000";
//			String beforCondition="<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
//			//上一节点id
//			String preknowledge=request.getParameter("lei");
//			/*if(preknowledge==null||"".equals(preknowledge))
//				throw new ServiceException("新增失败");*/
//			//添加子科目目录
//			if (request.getParameter("id")!="") {
//				//父节点id
//				String parentKnowledge=request.getParameter("id");
//
//				models.setId(id);
//				models.setContent(content);
//				models.setParentModel(parentKnowledge);
//				if (!preknowledge.isEmpty()) {
//					models.setPreModel(preknowledge);
//				}
//				models.setSubjectTree_Id(subjectTree_Id);
//				models.setImageIcons(imageIcons);
//				models.setModelContentId(knowledgecontentId);
//				models.setBeforCondition(beforCondition);
//				//向knowledge表新增数据
//				Boolean i=knowledgesServiceImp.inknowModels(models);
//					if (i==true) {
//						ZNodes zNodes=new ZNodes();
//						zNodes.setId(models.getId());
//						zNodes.setpId(models.getParentModel());
//						zNodes.setName(models.getContent());
//						zNodes.setKnowledgecontentId(models.getModelContentId());
//						return zNodes;
//					}
//			}
//			//添加科目目录
//			else if (request.getParameter("id")=="") {
//				//获取ROOT的ID
//				String parentKnowledge=knowledgesServiceImp.slRootModels(subjectTree_Id);
//				models.setId(id);
//				models.setContent(content);
//				models.setParentModel(parentKnowledge);
//				if (!preknowledge.isEmpty()) {
//					models.setPreModel(preknowledge);
//				}
//				models.setSubjectTree_Id(subjectTree_Id);
//				models.setImageIcons(imageIcons);
//				models.setModelContentId(knowledgecontentId);
//				models.setBeforCondition(beforCondition);
//
//
//				Boolean i=knowledgesServiceImp.inknowModels(models);
//
//				/*增加模型分类*/
//				ModelContact modelContact =new ModelContact();
//				modelContact.setId(id);
//				modelContact.setName(content);
//				modelContact.setParentId("0aec2588-4f01-4a94-b27a-fb2e20c8c85b");
//				mlsi.addModelContact(modelContact);
//				/*end*/
//
//				if (i==true) {
//					ZNodes zNodes=new ZNodes();
//					zNodes.setId(models.getId());
//					zNodes.setpId("0");
//					zNodes.setName(models.getContent());
//					zNodes.setKnowledgecontentId(models.getModelContentId());
//					return zNodes;
//				}
//			}
//
//				return null;
//		}
//
//
	
	
	/**
	 * 修改资源树节点名称
	 * @return
	 */
	@RequestMapping(value="beforeRename.action",method={RequestMethod.POST},produces="application/json;charset=utf-8")
	@ResponseBody
	public JsonResult beforeRename(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		String Id = URLDecoder.decode(request.getParameter("Id"), "UTF-8");
		String newname = URLDecoder.decode(request.getParameter("newname"), "UTF-8");
		ks.updateContent(Id,newname);
		return new JsonResult();
	}

//	/**
//	 * 修改资源树节点名称(成职模型库资源库修改）
//	 * @param newname
//	 * @param Id
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value="beforeRenameModel.action",method={RequestMethod.POST})
//	@ResponseBody
//	public void beforeRenameModel(HttpServletRequest request,HttpServletResponse response) throws Exception {
//		String newname=request.getParameter("newName");
//		String Id=request.getParameter("Id");
//		newname=java.net.URLDecoder.decode(newname,"utf-8");
//		ks.updateContent(Id,newname);
//
//		/*更改模型分类名字*/
//		ModelContact modelContact =new ModelContact();
//		modelContact.setId(Id);
//		modelContact.setName(newname);
//		//modelContact.setParentId("0aec2588-4f01-4a94-b27a-fb2e20c8c85b");
//		mlsi.updateModelContact(modelContact);
//		/*end*/
//		/*更改模型名称*/
//		Model model = new Model();
//		model.setContent(Id);
//		model.setName(newname);
//		mlsi.updateModel2(model);
//		/*end*/
//		/*修改知识点内容名称*/
//		adksi.updateKnowledgetConName(Id,newname);
//		/*end*/
//
////		return new JsonResult();
//	}
	//查询树下的资源
	@RequestMapping(value="treKnowlegcontent.action",method={RequestMethod.POST})
	@ResponseBody
	public List<Knowlegcontent> treKnowlegcontent(HttpServletRequest request, HttpServletResponse response){
		
		List<Knowlegcontent> knList=new ArrayList<Knowlegcontent>();
		knList=knowledgesServiceImp.SubjcetTree(request.getParameter("id"));
		
		return knList;
	}
	 
	//目录的新增
	@RequestMapping(value="ins.action",method={RequestMethod.POST})
	@ResponseBody
	public ZNodes ins(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Knowledges knowledges=new Knowledges();
		String id=UUID.randomUUID().toString();
		String userId = session.getAttribute("ID").toString();
		knowledges.setUserKey(userId);
		String content= request.getParameter("Content");
		if(content==null||content=="") {
			content="新建目录";
		}
		//科目树id
		String subjectTree_Id=request.getParameter("subid");
		String imageIcons="../../../Source/imgicon/tag_orange.png";
		String knowledgecontentId="00000000-0000-0000-0000-000000000000";
		String beforCondition="<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
		//上一节点id
		String preknowledge=request.getParameter("lei");
		/*if(preknowledge==null||"".equals(preknowledge)) 
			throw new ServiceException("新增失败");*/
		//添加子科目目录
		if (request.getParameter("id")!="") {
			//父节点id
			String parentKnowledge=request.getParameter("id");
			
			knowledges.setId(id);
			knowledges.setContent(content);
			knowledges.setParentKnowledge(parentKnowledge);
			if (!preknowledge.isEmpty()) {
				knowledges.setPreKnowledge(preknowledge);
			}
			knowledges.setSubjectTree_Id(subjectTree_Id);
			knowledges.setImageIcons(imageIcons);
			knowledges.setKnowledgecontentId(knowledgecontentId);
			knowledges.setBeforCondition(beforCondition);
			//向knowledge表新增数据
			Boolean i=knowledgesServiceImp.inknow(knowledges);
				if (i==true) {
					ZNodes zNodes=new ZNodes();
					zNodes.setId(knowledges.getId());
					zNodes.setpId(knowledges.getParentKnowledge());
					zNodes.setName(knowledges.getContent());
					zNodes.setKnowledgecontentId(knowledges.getKnowledgecontentId());
					return zNodes;
				}
		}
		//添加科目目录
		else if (request.getParameter("id")=="") {
			//获取ROOT的ID
			String parentKnowledge=knowledgesServiceImp.slRoot(subjectTree_Id);
			knowledges.setId(id);
			knowledges.setContent(content);
			knowledges.setParentKnowledge(parentKnowledge);
			if (!preknowledge.isEmpty()) {
				knowledges.setPreKnowledge(preknowledge);
			}
			knowledges.setSubjectTree_Id(subjectTree_Id);
			knowledges.setImageIcons(imageIcons);
			knowledges.setKnowledgecontentId(knowledgecontentId);
			knowledges.setBeforCondition(beforCondition);
			
			Boolean i=knowledgesServiceImp.inknow(knowledges);
			
			/*增加模型分类*/
			/*ModelContact modelContact =new ModelContact();
			modelContact.setId(id);
			modelContact.setName("new node1");
			modelContact.setParentId("0aec2588-4f01-4a94-b27a-fb2e20c8c85b");
			mlsi.addModelContact(modelContact);*/
			/*end*/
			
			if (i==true) {
				ZNodes zNodes=new ZNodes();
				zNodes.setId(knowledges.getId());
				zNodes.setpId("0");
				zNodes.setName(knowledges.getContent());
				zNodes.setKnowledgecontentId(knowledges.getKnowledgecontentId());
				return zNodes;
			}
		}
		
			return null;
	}
	
	//树的删除功能
	@RequestMapping(value="deTree.action",method={RequestMethod.POST})
	public void deTree(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String filePath=request.getParameter("filePath");
		String id=request.getParameter("id");
		//删除数据库中的文件夹
		Boolean i=knowledgesServiceImp.deTree(id,filePath);
		response.getWriter().print(i);
	}
	
	//编辑功能
	@RequestMapping(value="upRandom.action",method={RequestMethod.POST})
	public void upRandom(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		name = java.net.URLDecoder.decode(name,"UTF-8");
		if (knowledgesServiceImp.seleKContent(id)!=null) {
			Knowlegcontent knowlegcontent=new Knowlegcontent();
			knowlegcontent=knowledgesServiceImp.seleKContent(id);
			knowledgesServiceImp.upcontent(name, knowlegcontent.getId());
		};
		knowledgesServiceImp.upRandom(id, name);
		response.getWriter().print("true");
	}
	
	//文件下载到本机
	@RequestMapping(value="fileDownload.action",method={RequestMethod.POST})
	public void  fileDownload(HttpServletRequest request,HttpServletResponse response){
		String fileWay=request.getParameter("fileWay");
		String fileName=request.getParameter("customname");
		File file=new File(fileWay);
		try {
			InputStream fileInputStream=new FileInputStream(file);
			InputStream bufferedInputStream=new BufferedInputStream(fileInputStream);
			byte[] bs=new byte[bufferedInputStream.available()];
			bufferedInputStream.read(bs);
			bufferedInputStream.close();
			//清空response
			response.reset();
			//设置下载文件的默认名
			response.addHeader("content-type", "application/x-download");//文件编码,告诉浏览器你要下载
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);//下载的文件名
			response.addHeader("Content-Length", "" + file.length());//文件大小
			OutputStream bufferedOutputStream=new BufferedOutputStream(response.getOutputStream());//设置下载路径为浏览器路径
			
			bufferedOutputStream.write(bs);
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//拖动功能的实现
	@RequestMapping(value="drop.action",method={RequestMethod.POST})
	public void drop(HttpServletRequest request,HttpServletResponse response){
		String nextId=request.getParameter("nextId");//有可能为空
		String nextprevknowledgeId=request.getParameter("nextprevknowledgeId");//有可能为空
		String prevId=request.getParameter("prevId");//有可能为空
		String prevprevknowledgeId=request.getParameter("prevprevknowledgeId");//有可能为空
		String selfId=request.getParameter("selfId");
		String selfprevknowledgeId=request.getParameter("selfprevknowledgeId");//有可能为空
		String selfparentknowledgeId=request.getParameter("selfparentknowledgeId");//有可能为空
		if (!nextId.isEmpty()) {
			knowledgesServiceImp.upNext(nextId, nextprevknowledgeId);
		}
		if (!prevId.isEmpty()) {
			knowledgesServiceImp.upNext(prevId, prevprevknowledgeId);
		}
		knowledgesServiceImp.upknow(selfId, selfprevknowledgeId, selfparentknowledgeId);
	}
	
	
	
//	//主页模型目录的新增
//			@RequestMapping(value="AllinsModels.action",method={RequestMethod.POST})
//			@ResponseBody
//			public ZNodes AllinsModels(HttpServletRequest request,HttpServletResponse response,HttpSession session){
//				Models models=new Models();
//				String userId = session.getAttribute("ID").toString();
//				models.setUserKey(userId);
//				String id=UUID.randomUUID().toString();
//				String content= request.getParameter("Content");
//				if(content==null||content=="") {
//					content="新建目录";
//				}
//				//科目树id
//				String subjectTree_Id=request.getParameter("subid");
//				String imageIcons="../../../Source/imgicon/tag_orange.png";
//				String knowledgecontentId="00000000-0000-0000-0000-000000000000";
//				String beforCondition="<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
//				//上一节点id
//				//String preknowledge=request.getParameter("lei");
//				String preknowledge=adksi.lastModelsNodeIdInAll(subjectTree_Id,"",userId);
//				/*if(preknowledge==null||"".equals(preknowledge))
//					throw new ServiceException("新增失败");*/
//				//添加子科目目录
//				if (request.getParameter("id")!="") {
//					//父节点id
//					String parentKnowledge=request.getParameter("id");
//
//					models.setId(id);
//					models.setContent(content);
//					models.setParentModel(parentKnowledge);
//					if (!preknowledge.isEmpty()) {
//						models.setPreModel(preknowledge);
//					}
//					models.setSubjectTree_Id(subjectTree_Id);
//					models.setImageIcons(imageIcons);
//					models.setModelContentId(knowledgecontentId);
//					models.setBeforCondition(beforCondition);
//					//向knowledge表新增数据
//					Boolean i=knowledgesServiceImp.inknowModels(models);
//						if (i==true) {
//							ZNodes zNodes=new ZNodes();
//							zNodes.setId(models.getId());
//							zNodes.setpId(models.getParentModel());
//							zNodes.setName(models.getContent());
//							zNodes.setKnowledgecontentId(models.getModelContentId());
//							return zNodes;
//						}
//				}
//				//添加科目目录
//				else if (request.getParameter("id")=="") {
//					//获取ROOT的ID
//					String parentKnowledge=knowledgesServiceImp.slRootModels(subjectTree_Id);
//					models.setId(id);
//					models.setContent(content);
//					models.setParentModel(parentKnowledge);
//					if (StringUtils.isNotEmpty(preknowledge) /*!preknowledge.isEmpty()*/) {
//						models.setPreModel(preknowledge);
//					}
//					models.setSubjectTree_Id(subjectTree_Id);
//					models.setImageIcons(imageIcons);
//					models.setModelContentId(knowledgecontentId);
//					models.setBeforCondition(beforCondition);
//
//
//					Boolean i=knowledgesServiceImp.inknowModels(models);
//
//					/*增加模型分类*/
//					/*ModelContact modelContact =new ModelContact();
//					modelContact.setId(id);
//					modelContact.setName(content);
//					modelContact.setParentId("0aec2588-4f01-4a94-b27a-fb2e20c8c85b");
//					mlsi.addModelContact(modelContact);*/
//					/*end*/
//
//					if (i==true) {
//						ZNodes zNodes=new ZNodes();
//						zNodes.setId(models.getId());
//						zNodes.setpId("0");
//						zNodes.setName(models.getContent());
//						zNodes.setKnowledgecontentId(models.getModelContentId());
//						return zNodes;
//					}
//				}
//
//					return null;
//			}
	
//			//主页场景目录的新增
//			@RequestMapping(value="AllinsScenes.action",method={RequestMethod.POST})
//			@ResponseBody
//			public ZNodes AllinsScenes(HttpServletRequest request,HttpServletResponse response,HttpSession session){
//				Scenes scenes=new Scenes();
//				String userId = session.getAttribute("ID").toString();
//				scenes.setUserKey(userId);
//				String id=UUID.randomUUID().toString();
//				String content= request.getParameter("Content");
//				if(content==null||content=="") {
//					content="新建目录";
//				}
//				//科目树id
//				String subjectTree_Id=request.getParameter("subid");
//				String imageIcons="../../../Source/imgicon/tag_orange.png";
//				String knowledgecontentId="00000000-0000-0000-0000-000000000000";
//				String beforCondition="<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
//				//上一节点id
//				String preknowledge=adksi.lastScenesNodeIdInAll(subjectTree_Id,"",userId);//request.getParameter("lei");
//				/*if(preknowledge==null||"".equals(preknowledge))
//					throw new ServiceException("新增失败");*/
//				//添加子科目目录
//				if (request.getParameter("id")!="") {
//					//父节点id
//					String parentKnowledge=request.getParameter("id");
//
//					scenes.setId(id);
//					scenes.setContent(content);
//					scenes.setParentScene(parentKnowledge);
//					if (!preknowledge.isEmpty()) {
//						scenes.setPreScene(preknowledge);
//					}
//					scenes.setSubjectTree_Id(subjectTree_Id);
//					scenes.setImageIcons(imageIcons);
//					scenes.setSceneContentId(knowledgecontentId);
//					scenes.setBeforCondition(beforCondition);
//					//向knowledge表新增数据
//					Boolean i=knowledgesServiceImp.inknowScenes(scenes);
//						if (i==true) {
//							ZNodes zNodes=new ZNodes();
//							zNodes.setId(scenes.getId());
//							zNodes.setpId(scenes.getParentScene());
//							zNodes.setName(scenes.getContent());
//							zNodes.setKnowledgecontentId(scenes.getSceneContentId());
//							return zNodes;
//						}
//				}
//				//添加科目目录
//				else if (request.getParameter("id")=="") {
//					//获取ROOT的ID
//					String parentKnowledge=knowledgesServiceImp.slRootScenes(subjectTree_Id);
//					scenes.setId(id);
//					scenes.setContent(content);
//					scenes.setParentScene(parentKnowledge);
//					if (!preknowledge.isEmpty()) {
//						scenes.setPreScene(preknowledge);
//					}
//					scenes.setSubjectTree_Id(subjectTree_Id);
//					scenes.setImageIcons(imageIcons);
//					scenes.setSceneContentId(knowledgecontentId);
//					scenes.setBeforCondition(beforCondition);
//
//					Boolean i=knowledgesServiceImp.inknowScenes(scenes);
//
//					/*增加模型分类*/
//					ModelContact modelContact =new ModelContact();
//					modelContact.setId(id);
//					modelContact.setName(content);
//					modelContact.setParentId("7fb53e8f-baf1-4c1e-8868-bad634e81461");
//					mlsi.addModelContact(modelContact);
//					/*end*/
//
//					if (i==true) {
//						ZNodes zNodes=new ZNodes();
//						zNodes.setId(scenes.getId());
//						zNodes.setpId("0");
//						zNodes.setName(scenes.getContent());
//						zNodes.setKnowledgecontentId(scenes.getSceneContentId());
//						return zNodes;
//					}
//				}
//
//					return null;
//			}
	
}
