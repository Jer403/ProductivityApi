package com.example.DrawAwesome2.Network;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.DrawAwesome2.Jwt.JwtService;
import com.example.DrawAwesome2.models.LinkProjects;
import com.example.DrawAwesome2.models.NodeProjects;
import com.example.DrawAwesome2.models.Users;
import com.example.DrawAwesome2.repositories.LinkRepository;
import com.example.DrawAwesome2.repositories.NodeRepository;
import com.example.DrawAwesome2.repositories.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class NetworkService {


	private final UserRepository userRepository;
	private final NodeRepository nodeRepository;
	private final LinkRepository linkRepository;
	private final JwtService jwtService;
	
	
	

	public NetworkResponse create(NetworkRequest request) {
		String error = null;
		Users user;
		String ProjectName = "Untitled Project";
		if(request.getLang() == "es") {
			ProjectName = "Projecto sin nombre";
		}
		

		user = getUser(request);
		

		NodeProjects nodes = NodeProjects.builder()
							.userid(user.getId())
							.projectname(ProjectName)
							.project(null)
						    .build();
		

		try {
			nodeRepository.save(nodes);
		}catch(Exception e) {
			error = "repositoryCouldNotSave";
			return NetworkResponse.builder().error(error).build();
		}
		
		
		return NetworkResponse
				.builder()
				.projects(nodes.getId().toString())
				.error(error)
				.build();
	}
	
	
	
	
	
	public NetworkResponse getData(NetworkRequest request) {
		String error = null;
		Long id = request.getNodeId();
		NodeProjects node;
		LinkProjects link;
		
		
		
		try {
			node = nodeRepository.findById(id).orElseThrow();
		}catch(Exception e) {
			error = "repositoryCouldNotFound";
			return NetworkResponse.builder().error(error).build();
		}
		
		try {
			link = linkRepository.findByProjectid(id).orElseThrow();
		}catch(Exception e) {
			
			LinkProjects links = LinkProjects.builder()
					.projectid(node.getId())
					.project(null)
					.build();
			
			linkRepository.save(links);
			
			return NetworkResponse
					.builder()
					.nodeProject(node.getProject())
					.nodeProjectName(node.getProjectname())
					.linkProject(links.getProject())
					.build();
		}
		
		
		
		
		return NetworkResponse
				.builder()
				.nodeProject(node.getProject())
				.nodeProjectName(node.getProjectname())
				.linkProject(link.getProject())
				.build();
		
	}
	
	
	
	
	
	
	public NetworkResponse update(NetworkRequest request) {
		String error = null;
		Long id = request.getNodeId();
		String nodeProject = request.getNodeProject(); 
		String linkProject = request.getLinkProject(); 
		NodeProjects nodes;
		LinkProjects links;
		
		
		try {
			nodes = nodeRepository.findById(id).orElseThrow();
		}catch(Exception e) {
			error = "nodeProjectNotFound";
			return NetworkResponse.builder().error(error).build();
		}
		
		try {
			links = linkRepository.findByProjectid(id).orElseThrow();
		}catch(Exception e) {
			error = "linkProjectNotFound";
			return NetworkResponse.builder().error(error).build();
		}
		
		

		nodes.setProject(nodeProject);
		nodes.setProjectname(request.getNodeProjectName());
		links.setProject(linkProject);

		
		nodeRepository.save(nodes);
		linkRepository.save(links);
		
		
		return NetworkResponse
				.builder()
				.error(error)
				.build();
		
	}
	
	
	
	
	
	public NetworkResponse delete(NetworkRequest request) {
		String error = null;
		Long id = request.getNodeId();
		
		try {
			linkRepository.deleteByProjectid(id);
		}catch(Exception e) {
			error = "linkProjectNotFound";
			nodeRepository.deleteById(id);
			
			return NetworkResponse
					.builder()
					.error(error)
					.build();
		}
		
		
		
		nodeRepository.deleteById(id);
		
		return NetworkResponse
				.builder()
				.error(error)
				.build();
	}
	
	
	
	
	
	public NetworkResponse getAllProjects(NetworkRequest request) {
		String error = null;
		String ids = "";
		String names = "";
		int count = 0;
		Users user;

		user = getUser(request);
			
		List<NodeProjects> allNodes = nodeRepository.findByUserid(user.getId());
		
		
		
		for (NodeProjects node: allNodes){
			if(count == 0) {
				ids = ""+node.getId();
				names = ""+node.getProjectname();
				count++;
			}
			else{
				ids += "/"+node.getId();
				names += "/"+node.getProjectname();
				count++;
			}
		}
		
		String token = jwtService.getToken(user, request.getTiempo());
		
		return NetworkResponse
				.builder()
				.projects(ids)
				.nodeProjectName(names)
				.error(error)
				.token(token)
				.build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Users getUser( NetworkRequest request) {
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
	
	
	
}
