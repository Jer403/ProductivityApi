package com.example.DrawAwesome2.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import com.example.DrawAwesome2.ProjectManager.ProjectManagerRequest;
import com.example.DrawAwesome2.ProjectManager.ProjectManagerResponse;
import com.example.DrawAwesome2.ProjectManager.ProjectManagerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pm")
@RequiredArgsConstructor
public class ProjectManagerController {

	
	private final ProjectManagerService projectManagerService;

	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "createGroupImg")
	public ResponseEntity<ProjectManagerResponse> createGroupImg(@RequestParam("file") MultipartFile file, @RequestParam("content") String request) {

		return ResponseEntity.ok(projectManagerService.createNewGroupWithImg(file,request));
    }
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "createGroup")
	public ResponseEntity<ProjectManagerResponse> createGroup(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.createNewGroup(request));
    }

	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "updateGroup")
	public ResponseEntity<ProjectManagerResponse> updateGroup(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.updateGroup(request));
    }

	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "updateGroupImg")
	public ResponseEntity<ProjectManagerResponse> updateGroupWithImg(@RequestParam("file") MultipartFile file, @RequestParam("content") String request) {

		return ResponseEntity.ok(projectManagerService.updateGroupWithImg(file,request));
    }
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "getAllGroups")
	public ResponseEntity<ProjectManagerResponse> allGroups(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.getAllGroups(request));
    }
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "changeMemberPermiss")
	public ResponseEntity<ProjectManagerResponse> changeMemberPermiss(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.changeMemberPermiss(request));
    }

	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "deleteGroup")
	public ResponseEntity<ProjectManagerResponse> deleteGroup(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.deleteGroup(request));
	}

	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "leaveGroup")
	public ResponseEntity<ProjectManagerResponse> leaveGroup(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.leaveGroup(request));
	}


	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "createGroupList")
	public ResponseEntity<ProjectManagerResponse> createGroupList(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.createNewGroupList(request));
    }

	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "getGroupListData")
	public ResponseEntity<ProjectManagerResponse> groupListData(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.getGroupListData(request));
    }
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "getAllGroupLists")
	public ResponseEntity<ProjectManagerResponse> allGroupLists(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.getAllGroupLists(request));
    }
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "updateGroupList")
	public ResponseEntity<ProjectManagerResponse> updateGroupList(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.updateGroupList(request));
	}

	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "deleteGroupList")
	public ResponseEntity<ProjectManagerResponse> deleteGroupList(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.deleteGroupList(request));
	}

	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "getGroupCalendarDate")
	public ResponseEntity<ProjectManagerResponse> getGroupCalendarDate(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.getGroupCalendarDate(request));
    }
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "updateGroupCalendarDate")
	public ResponseEntity<ProjectManagerResponse> updateGroupCalendarDate(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.updateGroupCalendarDate(request));
	}
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "joinGroup")
	public ResponseEntity<ProjectManagerResponse> joinGroup(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.joinGroup(request));
	}
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "getGroupImg",produces = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<LinkedMultiValueMap<String, Object>> getGroupImg(@RequestParam("id") String request,@RequestParam("imgname") String imgName) {

		return ResponseEntity.ok(projectManagerService.getGroupImg(request, imgName));
	}
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "uploadFile")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("content") String request) {

		return ResponseEntity.ok(null);
	}

	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "getMembers")
	public ResponseEntity<ProjectManagerResponse> getMembers(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.getMembers(request));
	}
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "updateMemberPermiss")
	public ResponseEntity<ProjectManagerResponse> updateMemberPermiss(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.updateMemberPermiss(request));
	}
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "kickMember")
	public ResponseEntity<ProjectManagerResponse> kickMember(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.kickMember(request));
	}
	
	@CrossOrigin(origins = {"https://hrk118cb-8080.use2.devtunnels.ms", "http://127.0.0.1:5500"})
	@PostMapping(value = "inviteUser")
	public ResponseEntity<ProjectManagerResponse> inviteUser(@RequestBody ProjectManagerRequest request) {

		return ResponseEntity.ok(projectManagerService.inviteUser(request));
	}
	
}
