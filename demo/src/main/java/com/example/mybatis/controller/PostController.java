package com.example.mybatis.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.mybatis.dto.Member;
import com.example.mybatis.dto.Post;
import com.example.mybatis.mapper.MemberMapper;
import com.example.mybatis.mapper.PostMapper;

@Controller
public class PostController {

    @Autowired PostMapper postMapper;
    @Autowired MemberMapper memberMapper;

    @RequestMapping(value = "post/list", method = RequestMethod.GET)
    public String postMain(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int page,                    // defaultValue : 파라미터 값이 없을 때 default 값
                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        // 페이지네이션 포함
        int totalPostCount = postMapper.postAllCount();               // 전체 게시글 수
        int totalCount = totalPostCount / pageSize + 1;            // 총 페이지 수
        page = (page - 1) * pageSize;
        List<Post> postList = postMapper.postFindAll(page, pageSize);

        model.addAttribute("posts", postList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalCount);
        return "post/postMain";
    }

    @RequestMapping(value = "post/list", method = RequestMethod.POST)
    public String postMain(Model model,
                           @RequestParam("select") String select,
                           @RequestParam("search") String search) {

        model.addAttribute("select", select);

        if (select.equals("title")) {
            List<Post> posts = postMapper.postFindByTitle(search, 0, 7);
            model.addAttribute("posts", posts);
            return "post/postMainTitle";
        }

        List<Post> posts = postMapper.postFindByNickName(search, 0, 7);
        model.addAttribute("posts", posts);
        return "post/postMainNickName";
    }

    @RequestMapping(value = "post/view", method = RequestMethod.GET)
    public String postView(Model model,
                           @RequestParam("postId") int postId,
                           HttpSession session) {
    	
        Post post = postMapper.findByPostId(postId);
        post.setCount(post.getCount() + 1);
        postMapper.postUpdate(post);
        String loginId = (String)session.getAttribute("userId");
        Member member = memberMapper.findMemberByLoginId(loginId);
        model.addAttribute("posts", post);
        
        if (post.getName().equals(member.getLoginId())) {
            return "post/postView";
        }

       return "post/postOnlyView";

    }

    @RequestMapping(value = "post/write", method = RequestMethod.GET)
    public String postWrite(Model model, HttpSession session) {
        String loginId = (String)session.getAttribute("userId");
        Member member = memberMapper.findMemberByLoginId(loginId);
        model.addAttribute("memberId", loginId);
        model.addAttribute("member", member);

        return "post/writePost";
    }

    @RequestMapping(value = "post/write", method = RequestMethod.POST)
    public String postWrite(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("nickname") String nickname,
                            @RequestParam("memberId") String memberId,
                            @RequestParam("uploadFile") MultipartFile uploadFile, HttpSession session) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Post post = new Post(memberId, title, content, nickname, 1, sdf.format(new Date()),"","");
       
        //파일업로드
        if(uploadFile != null && !uploadFile.isEmpty()) {//업로드 파일이 존재할 때
			String renameFileName =  saveFile(uploadFile,session);
			
			if(renameFileName != null) {
				post.setOriginalFileName(uploadFile.getOriginalFilename());
				post.setRenameFileName(renameFileName);
			}
		}
        
        postMapper.insertPost(post);
        return "redirect:/post/list";
    }
    
	public String saveFile(MultipartFile file,HttpSession session) {
	
		//String root = session.getServletContext().getRealPath("resources");
		String root = "C:\\Image";	
		File folder = new File(root);
		
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String originFileName = file.getOriginalFilename();
		String renameFileName 
			= sdf.format(new Date(System.currentTimeMillis()))
				+ "." + originFileName.substring(originFileName.lastIndexOf(".") +1);
		
		String renamePath = folder + "\\" + renameFileName;
		
		try {
			file.transferTo(new File(renamePath));
		} catch (Exception e) {
			System.out.println("파일 전송 에러:" + e.getMessage());
			e.printStackTrace();
		}
		
		return renameFileName;
    		
    }

    @RequestMapping(value = "post/update", method = RequestMethod.GET)
    public String postUpdateRedirect(Model model,
                                     @RequestParam("postId") int postId) {
        Post post = postMapper.findByPostId(postId);
        model.addAttribute("posts", post);
        return "post/postUpdate";
    }

    @RequestMapping(value = "post/update", method = RequestMethod.POST)
    public String postUpdate(@RequestParam("postId") int postId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("uploadFile") MultipartFile uploadFile, HttpSession session) {
        Post post = postMapper.findByPostId(postId);
        post.setTitle(title);
        post.setContent(content);
        post.setCount(post.getCount());
        
        //파일업로드
        if(uploadFile != null && !uploadFile.isEmpty()) {//업로드 파일이 존재할 때
			String renameFileName =  saveFile(uploadFile,session);
			
			if(renameFileName != null) {
				post.setOriginalFileName(uploadFile.getOriginalFilename());
				post.setRenameFileName(renameFileName);
			}
		}
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        postMapper.postUpdate(post);

        return "redirect:/post/list";
    }

    @RequestMapping(value = "post/delete", method = RequestMethod.GET)
    public String postDelete(@RequestParam("postId") int postId) {
        postMapper.deletePost(postId);
        return "redirect:/post/list";
    }


    @RequestMapping(value = "notice", method = RequestMethod.GET)
    public String notice() {
        return "notice/notice";
    }
 
    @RequestMapping(value = "/post/view/download")
    public void doDownloadFile(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
    	String root = "C:\\Image\\";		

    	String fileName = request.getParameter("flnm");
    	String oflnm = request.getParameter("oflnm");
    	
    	File file = new File(root+ fileName);
	 
        FileInputStream fileInputStream = null;
        ServletOutputStream servletOutputStream = null;
     
        try{
            String downName = null;
            String browser = request.getHeader("User-Agent");
            //파일 인코딩
            if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){//브라우저 확인 파일명 encode  
                
                downName = URLEncoder.encode(oflnm,"UTF-8").replaceAll("\\+", "%20");
                
            }else{
                
                downName = new String(oflnm.getBytes("UTF-8"), "ISO-8859-1");
                
            }
           
            response.setHeader("Content-Disposition","attachment;filename=\"" + downName+"\"");             
            response.setContentType("application/octer-stream");
            response.setHeader("Content-Transfer-Encoding", "binary;");

            fileInputStream = new FileInputStream(file);

            servletOutputStream = response.getOutputStream();
   
            byte b [] = new byte[1024];
            int data = 0;
     
            while((data=(fileInputStream.read(b, 0, b.length))) != -1){
                
                servletOutputStream.write(b, 0, data);
                
            }

            servletOutputStream.flush();//출력
       
        }catch (Exception e) {
            e.printStackTrace();
        
        }finally{
            if(servletOutputStream!=null){
                try{
                    servletOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(fileInputStream!=null){
                try{
                    fileInputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

