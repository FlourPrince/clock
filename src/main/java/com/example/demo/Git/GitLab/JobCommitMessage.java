package com.example.demo.Git.GitLab;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.example.demo.Git.Model.*;
import jersey.repackaged.com.google.common.collect.Lists;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.UserApi;
import org.gitlab4j.api.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/*-*
 * The <code>com.example.demo.Git.GitLab.JobCommitMessage</code> class have to be described
 * <p>
 * The <code>JobCommitMessage</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/2/23 15:54
 * @see
 * @since 1.0
 */
public class JobCommitMessage {
	private  String hostUrl;
	private  String privateToken;
	/**
	 * 日志处理类
	 */
	public JobCommitMessage(String hostUrl, String privateToken) {
		System.out.println("初始化成功");
		this.hostUrl = hostUrl;
		this.privateToken = privateToken;

	}

	/**
	 * 获取所有用户
	 * @return
	 */
	public List<User> getUser() {
		GitLabApi gitLabApi=new GitLabApi(hostUrl,privateToken);
		UserApi userApi = gitLabApi.getUserApi();
		List<User> baseUsers=new ArrayList<>();
		try {
			baseUsers = userApi.getUsers(1, 100);
		} catch (GitLabApiException e) {
			e.printStackTrace();
		}
		return baseUsers;
	}

	/**
	 * 获取所有项目
	 * @return
	 */
	public List<ProjectsVo> getProjects() {
		List<ProjectsVo> projectsVos = Lists.newArrayList();
		int i = 1;
		while (true) {
			HttpResponse httpResponse = HttpRequest.
					get(GitlabUrlConstants.getProjectUrl(hostUrl,
							privateToken, String.valueOf(i))).execute();
			JSONArray jsonArray = JSONUtil.parseArray(httpResponse.body());
			if (jsonArray.size() > 0) {
				i++;
				projectsVos.addAll(JSONUtil.toList(jsonArray, ProjectsVo.class));
			} else {
				break;
			}
		}
		return projectsVos;
	}

	/**
	 * 获取所有分支
	 *
	 * @param projectId
	 * @return
	 */
	public List<BranchesVo> getBranches(String projectId) {
		HttpResponse httpResponse = HttpRequest.get(GitlabUrlConstants.getBrancheUrl(hostUrl, projectId, privateToken)).execute();
		JSONArray jsonArray = JSONUtil.parseArray(httpResponse.body());
		return JSONUtil.toList(jsonArray, BranchesVo.class);
	}


	/**
	 * 获取所有提交记录
	 *
	 * @param projectId
	 * @param brancheName
	 * @param since
	 * @param until
	 * @return
	 */
	public List<CommitsVo> getCommits(String projectId, String brancheName, String since, String until) {
		List<CommitsVo> commitVos = Lists.newArrayList();
		int i = 1;
		while (true) {
			HttpResponse httpResponse = HttpRequest.get(GitlabUrlConstants.getCommitUrl(hostUrl, projectId, String.valueOf(i), brancheName, since, until, privateToken)).execute();
			JSONArray jsonArray = JSONUtil.parseArray(httpResponse.body());
			if (jsonArray.size() > 0) {
				i++;
				commitVos.addAll(JSONUtil.toList(jsonArray, CommitsVo.class));
			} else {
				break;
			}
		}
		return commitVos;
	}

	/**
	 * 获取提交记录详细
	 *
	 * @param projectId
	 * @param commitId
	 * @return
	 */
	public CommitDetailVo getCommitDetail(String projectId, String commitId) {
		HttpResponse httpResponse = HttpRequest.get(GitlabUrlConstants.getCommitDetailUrl(hostUrl, projectId, commitId, privateToken)).execute();
		return JSONUtil.toBean(httpResponse.body(), CommitDetailVo.class);
	}

	/**
	 * 获取分支版本跟master比对提交记录
	 * @param projectId
	 * @param from
	 * @param to
	 * @return
	 */
	public CompareCommitsVo getCompareBranchesCommitDetail(String projectId,String from,String to) {
		HttpResponse httpResponse = HttpRequest.get(GitlabUrlConstants.getCompareBranchesCommitDetail(
				hostUrl, projectId,from,to,privateToken)).execute();
		return JSONUtil.toBean(httpResponse.body(), CompareCommitsVo.class);
	}
}
