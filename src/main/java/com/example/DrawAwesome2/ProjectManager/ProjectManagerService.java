package com.example.DrawAwesome2.ProjectManager;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


import org.springframework.core.io.FileSystemResource;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.example.DrawAwesome2.Jwt.JwtService;
import com.example.DrawAwesome2.models.GroupCalendarListProjects;
import com.example.DrawAwesome2.models.GroupListProjects;
import com.example.DrawAwesome2.models.Groups;
import com.example.DrawAwesome2.models.MemberLinks;
import com.example.DrawAwesome2.models.Message;
import com.example.DrawAwesome2.models.Users;
import com.example.DrawAwesome2.repositories.GroupCalendarListRepository;
import com.example.DrawAwesome2.repositories.GroupListRepository;
import com.example.DrawAwesome2.repositories.GroupRepository;
import com.example.DrawAwesome2.repositories.MemberLinkRepository;
import com.example.DrawAwesome2.repositories.UserRepository;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProjectManagerService {

	private final UserRepository userRepository;
	private final GroupRepository groupRepository;
	private final GroupListRepository groupListRepository;
	private final GroupCalendarListRepository groupCalendarListRepository;
	private final MemberLinkRepository memberLinkRepository;
	private final JwtService jwtService;
	private final SimpMessageSendingOperations messagingService;
	String Messagebroker = "/group/";

	
	
	
	
	
	
	

	public ProjectManagerResponse createNewGroupWithImg(MultipartFile file, String request) {
		String error = null;
		String joinlink = UUID.randomUUID().toString();
		
		ProjectManagerRequest requestData = transformFromJson(request);
		String GroupName = requestData.getGroupName();
		Users user = getUser(requestData);
		
		String filename = uploadFile(file, "groupImgs");
		
		
		Groups group = Groups.builder()
							.groupname(GroupName)
							.useradmin(user.getId())
							.joinlink(joinlink)
							.img(filename)
						    .build();

		groupRepository.save(group);
		
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.groupName(group.getGroupname())
				.groupId(group.getId())
				.build();
	}
	
	
	

	public ProjectManagerResponse createNewGroup(ProjectManagerRequest request) {
		String error = null;
		String joinlink = UUID.randomUUID().toString();
		
		String GroupName = request.getGroupName();
		Users user = getUser(request);
		
		
		Groups group = Groups.builder()
							.groupname(GroupName)
							.useradmin(user.getId())
							.joinlink(joinlink)
							.img("group.jpg")
						    .build();

		groupRepository.save(group);
		
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.groupName(group.getGroupname())
				.groupId(group.getId())
				.build();
	}
	
	
	
	

	public ProjectManagerResponse createNewGroupList(ProjectManagerRequest request) {
		String error = null;
		String ListName = "Untitled Group List";
		Groups group;
		Users user = getUser(request);
		
		if(request.getLang().equals("es")) {
			ListName = "Lista sin nombre";
		}

		group = getGroup(request.getGroupId().toString());

		if(user.getId() != group.getUseradmin()) {
			System.out.println("ahhh es un no admin jajajja");
			error = "userIsNotAdmin";
			return ProjectManagerResponse.builder().error(error).build();
		}
		

		GroupListProjects lists = GroupListProjects.builder()
							.groupid(group.getId())
							.projectname(ListName)
							.project(null)
						    .build();
		

		try {
			groupListRepository.save(lists);
		}catch(Exception e) {
			error = "repositoryCouldNotSave";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		
		return ProjectManagerResponse
				.builder()
				.projects(lists.getId().toString())
				.error(error)
				.build();
		
	}
	
	public ProjectManagerResponse updateGroup(ProjectManagerRequest request) {
		
		
		String error = null;
		String newName = request.getGroupName();
		Groups group = getGroup(request.getGroupId().toString());
		Users user = getUser(request);
		
		if(user.getId() != group.getUseradmin()) {
			error = "userIsNotAdmin";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		
		group.setGroupname(newName);
		
		groupRepository.save(group);
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.build();
		
	}
	
	
	public ProjectManagerResponse updateGroupWithImg(MultipartFile file ,String requestData) {
		ProjectManagerRequest request = transformFromJson(requestData);
		
		
		String error = null;
		String newName = request.getGroupName();
		String newImg = null;
		Groups group = getGroup(request.getGroupId().toString());
		Users user = getUser(request);
		
		if(user.getId() != group.getUseradmin()) {
			error = "userIsNotAdmin";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		if(group.getImg() == "group.jpg" && !file.equals("null")) {
			newImg = uploadFile(file, "groupImgs");
			
		}
		
		else if(group.getImg() != "group.jpg" && !file.equals("null")) {
			try {
				Path path = Paths.get("src/main/resources/groupImgs/"+ group.getImg());
				Files.deleteIfExists(path);
			}catch(Exception e) {
				error = "errorWhileDeletingFile";
				return ProjectManagerResponse.builder().error(error).build();
			}
			
			newImg = uploadFile(file, "groupImgs");
		}
		
		group.setGroupname(newName);
		if(!newImg.equals("null")) {
			group.setImg(newImg);
		}
		
		groupRepository.save(group);
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.img(newImg)
				.build();
		
	}
	
	
	
	
	
	

	
	public ProjectManagerResponse updateGroupList(ProjectManagerRequest request) {
		String error = null;
		Long id = request.getGroupListId();
		String listProject = request.getListProject(); 
		GroupListProjects list;
		
		
		try {
			list = groupListRepository.findById(id.toString()).orElseThrow();
		}catch(Exception e) {
			error = "listProjectNotFound";
			return ProjectManagerResponse.builder().error(error).build();
		}
		

		list.setProject(listProject);
		list.setProjectname(request.getListProjectName());

		try {
			groupListRepository.save(list);
		}catch(Exception e) {
			error = "repositoryCouldNotSave";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		String mess = "{\"listProject\":\""+list.getProject()+"\",\"listProjectName\":\""+list.getProjectname()+"\",\"userId\":\""+request.getUserId()+"\"}";
		
		messagingService.convertAndSend( Messagebroker+list.getId() , mess);
		
		
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.build();
		
	}
	
	public ProjectManagerResponse changeMemberPermiss(ProjectManagerRequest request) {
		String error = null;
		Users user = getUser(request);
		Groups group = getGroup(request.getGroupId().toString());
		MemberLinks link ;
		
		
		if(user.getId() != group.getUseradmin()) {
			error = "userIsNotAdmin";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		
		try {
			link = memberLinkRepository.findByGroupidAndUserid(group.getId(), request.getMemberId()).orElseThrow();
		}catch(Exception e) {
			error = "errorFindingMember";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		link.setPermis(request.getPermis());
		

		try {
			memberLinkRepository.save(link);
		}catch(Exception e) {
			error = "errorSavingLink";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.build();
		
	}
	
	
	
	
	public ProjectManagerResponse getGroupListData(ProjectManagerRequest request) {
		String error = null;
		Long id = request.getGroupListId();
		GroupListProjects list;
		
		
		
		try {
			list = groupListRepository.findById(id.toString()).orElseThrow();
		}catch(Exception e) {
			error = "repositoryCouldNotFound";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		return ProjectManagerResponse
				.builder()
				.listProject(list.getProject())
				.listProjectName(list.getProjectname())
				.build();
		
	}
	
	
	public ProjectManagerResponse getAllGroupLists(ProjectManagerRequest request) {
		String error = null;
		String ids = "";
		String names = "";
		int count = 0;
		Groups group;
		Users user;
		String admin = "false";
		String permis = "0";
		
		user = getUser(request);
		group = getGroup(request.getGroupId().toString());
		
		
		try{
			if(!(group.getUseradmin() == user.getId())) {
				MemberLinks link = memberLinkRepository.findByGroupidAndUserid(group.getId(), user.getId()).orElseThrow();
				permis = link.getPermis()+"";
			}
			
		}catch(Exception e) {
			error = "userDoesNotBelong";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
			
		List<GroupListProjects> allLists = groupListRepository.findByGroupid(group.getId());
		
		
		
		for (GroupListProjects list: allLists){
			if(count == 0) {
				ids = ""+list.getId();
				names = ""+list.getProjectname();
				count++;
			}
			else{
				ids += "/"+list.getId();
				names += "/"+list.getProjectname();
				count++;
			}
		}
		
		if(user.getId() == group.getUseradmin()) {
			admin = "true";
		}
		
		return ProjectManagerResponse
				.builder()
				.projects(ids)
				.listProjectName(names)
				.admin(admin)	
				.membersPermis(permis)
				.error(error)
				.build();
	}
	
	
	public ProjectManagerResponse getAllGroups(ProjectManagerRequest request) {
		String error = null;
		String ids = "";
		String names = "";
		int count = 0;
		Users user;
		List<Groups> allGroups = new LinkedList();
		
		user = getUser(request);
			
		List<MemberLinks> allLinks = memberLinkRepository.findByUserid(user.getId());
		List<Groups> allAdminGroups = groupRepository.findByUseradmin(user.getId());
		
		
		for(Groups group: allAdminGroups) {
			try {
				allGroups.add(group);
			}catch(Exception e) {
				error = "repositoryCouldNotFound";
				return ProjectManagerResponse.builder().error(error).build();
			}
		}

		
		for(MemberLinks link: allLinks) {
			try {
				Groups group = groupRepository.findById(link.getGroupid().toString()).orElseThrow();
				allGroups.add(group);
			}catch(Exception e) {
				error = "repositoryCouldNotFound";
				return ProjectManagerResponse.builder().error(error).build();
			}
			
		}
		
		if(allGroups.equals(null)) {
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		for (Groups group: allGroups){
			if(count == 0) {
				ids = ""+group.getId();
				names = ""+group.getGroupname();
				count++;
			}
			else{
				ids += "/"+group.getId();
				names += "/"+group.getGroupname();
				count++;
			}
		}
		
		String token = jwtService.getToken(user, request.getTiempo());
		
		return ProjectManagerResponse
				.builder()
				.groups(ids)
				.groupNames(names)
				.token(token)
				.userId(user.getId().toString())
				.error(error)
				.build();
	}
	
	
	
	
	
	public ProjectManagerResponse deleteGroupList(ProjectManagerRequest request) {
		String error = null;
		Long id = request.getGroupListId();
		GroupListProjects list;
		Groups group;

		group = getGroup(request.getGroupId().toString());
		
		
		try {
			list = groupListRepository.findById(id.toString()).orElseThrow();
		}catch(Exception e) {
			error = "repositoryCouldNotFind";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		if(list.getGroupid() == group.getId()) {
			try {
				groupListRepository.deleteById(id);
			}catch(Exception e) {
				error = "repositoryCouldNotDelete";
				return ProjectManagerResponse
						.builder()
						.error(error)
						.build();
			}
		}
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.build();
	}
	
	

	public ProjectManagerResponse deleteGroup(ProjectManagerRequest request) {
		String error = null;
		Groups group;
		Users user;
		List<MemberLinks> links;
		List<GroupListProjects> lists;
		
		user = getUser(request);
		group = getGroup(request.getGroupId().toString());
		
		if(user.getId() != group.getUseradmin()) {
			error = "userIsNotAdmin";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		try {
			links = memberLinkRepository.findByGroupid(group.getId());
		}catch(Exception e) {
			error = "repositoryCouldNotFind";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		for(MemberLinks link: links) {
			try {
				memberLinkRepository.delete(link);
			}catch(Exception e) {
				error = "repositoryCouldNotDeleteLink";
				return ProjectManagerResponse
						.builder()
						.error(error)
						.build();
			}
		}
		
		try {
			lists = groupListRepository.findByGroupid(group.getId());
		}catch(Exception e) {
			error = "repositoryCouldNotFind";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		for(GroupListProjects link: lists) {
			try {
				groupListRepository.delete(link);
			}catch(Exception e) {
				error = "repositoryCouldNotDeleteList";
				return ProjectManagerResponse
						.builder()
						.error(error)
						.build();
			}
		}
		
		try {
			groupRepository.delete(group);
		}catch(Exception e) {
			error = "repositoryCouldNotDeleteGroup";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		System.out.println(error);
		return ProjectManagerResponse
				.builder()
				.error(error)
				.build();
	}
	
	
	
	
	
	public ProjectManagerResponse updateGroupCalendarDate(ProjectManagerRequest request) {
		String error = null;
		String listProject = request.getListProject(); 
		String date = request.getDate(); 
		GroupCalendarListProjects list;
		Groups group;	
		

		group = getGroup(request.getGroupId().toString());
		
		try {
			list = groupCalendarListRepository.findByGroupidAndDate(group.getId(),date).orElseThrow();
		}catch(Exception e) {
			GroupCalendarListProjects newList = GroupCalendarListProjects.builder()
					.project(request.getListProject())
					.groupid(group.getId())
					.date(date)
					.build();
			
			groupCalendarListRepository.save(newList);
			
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		
		list.setProject(listProject);
		groupCalendarListRepository.save(list);
		
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.build();
		
	}
	
	

	
	public ProjectManagerResponse getGroupCalendarDate(ProjectManagerRequest request) {
		String error = null;
		String date = request.getDate();
		GroupCalendarListProjects list;
		Groups group;
		

		group = getGroup(request.getGroupId().toString());
		
		try {
			list = groupCalendarListRepository.findByGroupidAndDate(group.getId(),date).orElseThrow();
		}catch(Exception e) {
			error = "couldNotFoundAListForTheSpecifiedDate";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		return ProjectManagerResponse
				.builder()
				.listProject(list.getProject())
				.build();
		
	}
	
	public ProjectManagerResponse leaveGroup(ProjectManagerRequest request) {
		String error = null;
		Users user;
		Groups group;
		
		user = getUser(request);
		group = getGroup(request.getGroupId().toString());
		
		if(user.getId() == group.getUseradmin()) {
			error = "userCannotLeaveOwnGroup";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		
		try{
			MemberLinks link = memberLinkRepository.findByGroupidAndUserid(group.getId(), user.getId()).orElseThrow();

			memberLinkRepository.delete(link);
			
		}catch(Exception e) {
			error = "repositoryCouldNotDelete";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.build();
		
	}
	
	
	
	
	

	
	public ProjectManagerResponse joinGroup(ProjectManagerRequest request) {
		String error = null;
		String joinlink = request.getJoinlink();
		Users user;
		Groups group;
		List<MemberLinks> links;
		
		user = getUser(request);
		
		try{
			group = groupRepository.findByJoinlink(joinlink).orElseThrow();
		}catch(Exception e) {
			error = "repositoryCouldNotFindSpecifiedGroup";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		try {
			links = memberLinkRepository.findByUserid(user.getId());
		}catch(Exception e) {
			error = "errorFindingLinks";
			return ProjectManagerResponse.builder().error(error).build();
		}

		
		for(MemberLinks link: links) {
			if(group.getId().equals(link.getGroupid())) {
				error = "userAlreadyBelongToThatGroup";
				return ProjectManagerResponse.builder().error(error).build();
			}
		}
		
		
		MemberLinks link = MemberLinks.builder()
				.userid(user.getId())
				.groupid(group.getId())
				.permis(1)
				.build();
		
		memberLinkRepository.save(link);
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.groupName(group.getGroupname())
				.groupId(group.getId())
				.build();
		
	}
	
	
	
	
	


	public ProjectManagerResponse getMembers(ProjectManagerRequest request) {
		String error = null;
		List<MemberLinks> links;
		String membersId;
		String membersName;
		String membersPermis;
		int membersAmount;
		Users admin;
		Groups group = getGroup(request.getGroupId().toString());
		
		
		
		try {
			links = memberLinkRepository.findByGroupid(request.getGroupId());
		}catch(Exception e) {
			error = "errorFindingLinks";
			return ProjectManagerResponse.builder().error(error).build();
		}


		try {
			admin = userRepository.findById(group.getUseradmin().toString()).orElseThrow();
		}catch(Exception e) {
			error = "errorFindingUser";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		membersId = admin.getId()+"";
		membersName = admin.getUsername();
		membersPermis = "4";
		membersAmount = 1;
		
		
		for(MemberLinks link: links) {
			Users user;
			try {
				user = userRepository.findById(link.getUserid().toString()).orElseThrow();
				membersId += "/"+user.getId();
				membersName += "/"+user.getUsername();
				membersPermis += "/"+link.getPermis();
			}catch(Exception e) {
				error = "errorFindingUser";
				return ProjectManagerResponse.builder().error(error).build();
			}
			membersAmount++;
		}
		
		
		
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.membersId(membersId)
				.membersName(membersName)
				.membersPermis(membersPermis)
				.groupJoinlink(group.getJoinlink())
				.build();
		
	}
	
	
	
	
	

	public ProjectManagerResponse inviteUser(ProjectManagerRequest request) {
		String error = null;

		Users userAdder = getUser(request);
		Groups group = getGroup(request.getGroupId().toString());
		MemberLinks adderLink;
		Users userToAdd;
		
		if(userAdder.getId() != group.getUseradmin()) {
			try {
				adderLink = memberLinkRepository.findByGroupidAndUserid(group.getId(), userAdder.getId()).orElseThrow();
			}catch(Exception e) {
				error = "errorFindingAdderLink";
				return ProjectManagerResponse.builder().error(error).build();
			}
			if(!(adderLink.getPermis() >= 3)) {
				error = "adderDoesNotHavePermis";
				return ProjectManagerResponse.builder().error(error).build();
			}
		}
		

		try {
			userToAdd = userRepository.findByUsername(request.getUserName()).orElseThrow();
		}catch(Exception e) {
			error = "errorFindingUser";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		
		try {
			adderLink = memberLinkRepository.findByGroupidAndUserid(group.getId(), userToAdd.getId()).orElseThrow();
			
			
			error = "userAlreadyBelongToTheGroup";
			return ProjectManagerResponse.builder().error(error).build();
		}catch(Exception e) {
		}
		
		
		MemberLinks link = MemberLinks.builder()
				.userid(userToAdd.getId())
				.groupid(group.getId())
				.permis(1)
				.build();
		
		try {
			memberLinkRepository.save(link);
		}catch(Exception e) {
			error = "errorSavingLink";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.build();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	public String uploadFile(MultipartFile requestFile, String pathFolder ) {
		String error = null;
		String newFileName;
		
		try {
			MultipartFile file = requestFile;
			
			String fileName = UUID.randomUUID().toString();
			byte[] bytes = file.getBytes();
			String fileOgName = file.getOriginalFilename();
			
			long fileSize = file.getSize();
			long maxFileSize = 5 *1024 * 1024;
			
			if(fileSize > maxFileSize) {
				error = "fileMustBeLessThan5Mb";
				return error;
			}
			
			if(!fileOgName.endsWith(".jpg") &&
			   !fileOgName.endsWith(".jpeg") &&
			   !fileOgName.endsWith(".png")){
				error = "fileExtensionNotSupported";
				return error;
			}
			
			
			String fileExtension = fileOgName.substring(fileOgName.lastIndexOf("."));
			newFileName = fileName + fileExtension;
			
			File folder = new File("src/main/resources/"+pathFolder);
			
			if(!folder.exists()) {
				folder.mkdirs();
			}
			
			Path path = Paths.get("src/main/resources/"+pathFolder+"/"+ newFileName);
			Files.write(path, bytes);
			
		}catch(Exception e) {
			error = "errorUploadingImg";
			return error;
		}
		return newFileName;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public LinkedMultiValueMap<String, Object> getGroupImg(String request, String imgName) {
		Groups group = getGroup(request);
		LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();

		
		if(!imgName.equals("null")) {
			if(group.getImg().equals(imgName)) {
				formData.add("log", "imageIsTheSame");
				return formData;
			}
		}

		try {
			
	        formData.add("file", new FileSystemResource("src/main/resources/groupImgs/"+group.getImg()));
	        
		}catch(Exception e) {
			return null;
		}
		
		
		return formData;
		
	}
	
	
	
	
	
	
	public ProjectManagerResponse updateMemberPermiss(ProjectManagerRequest request) {
		String error = null;
		Groups group = getGroup(request.getGroupId().toString());
		Users useradmin = getUser(request);
		MemberLinks link;
		
		
		if(useradmin.getId() != group.getUseradmin()) {
			error = "userIsNotAdmin";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		
		try {
			link = memberLinkRepository.findByGroupidAndUserid(request.getGroupId(), request.getMemberId()).orElseThrow();
		}catch(Exception e) {
			error = "errorFindingLink";
			return ProjectManagerResponse.builder().error(error).build();
		}

		link.setPermis(request.getPermis());

		try {
			memberLinkRepository.save(link);
		}catch(Exception e) {
			error = "errorSaveingLink";
			return ProjectManagerResponse.builder().error(error).build();
		}
		
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.build();
		
	}
	
	
	

	
	public ProjectManagerResponse kickMember(ProjectManagerRequest request) {
		String error = null;
		Users admin = getUser(request);
		Groups group = getGroup(request.getGroupId().toString());
		
		if(admin.getId() != group.getUseradmin()) {
			error = "userIsNotAdmin";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		if(request.getMemberId() == group.getUseradmin()) {
			error = "adminCannotLeaveOwnGroup";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		
		try{
			MemberLinks link = memberLinkRepository.findByGroupidAndUserid(group.getId(), request.getMemberId()).orElseThrow();

			memberLinkRepository.delete(link);
			
		}catch(Exception e) {
			error = "repositoryCouldNotDelete";
			return ProjectManagerResponse
					.builder()
					.error(error)
					.build();
		}
		
		
		return ProjectManagerResponse
				.builder()
				.error(error)
				.build();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	
	


	public Users getUser( ProjectManagerRequest request) {
		Users user = null;
		String username = null;
		
		try {
			username = jwtService.getUsernameFromToken(request.getToken());
		}catch(Exception e) {
			return user;
		}
		
		
		try {
			user = userRepository.findByUsername(username).orElseThrow();
		}catch(Exception e) {
			return user;
		}

		return user;
	}
	public ProjectManagerRequest transformFromJson( String request) {
		Gson gson = new Gson();
		return gson.fromJson(request, ProjectManagerRequest.class);
	}
	
	public Groups getGroup( String id) {
		Groups group = null;
		
		try {
			group = groupRepository.findById(id).orElseThrow();
		}catch(Exception e) {
			return null;
		}

		return group;
	}
	
}
