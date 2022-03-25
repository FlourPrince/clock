package com.example.demo.Svn;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.ClassPathResource;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * The <code>com.example.demo.Svn.SvnUtil</code> class have to be described
 * <p>
 * The <code>SvnUtil</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/2/28 20:25
 * @see
 * @since 1.0
 */
public class SvnUtil {

	private Logger logger = LoggerFactory.getLogger(SvnUtil.class);

	static {
		DAVRepositoryFactory.setup();
	}

//    @Inject(instance = PropertyUtil.class)
//    private PropertyUtil propertyUtil;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	private SVNClientManager manager;
	private SVNUpdateClient updateClient;
	private String url;
	private String userName;
	private String passWord;


	public SvnUtil(String userName, String passWord) {
		init(userName, passWord);
	}

	public SvnUtil(String userName, String passWord, String url) {
		this(userName, passWord);
		this.url = url;
	}

	private void init(String userName, String passWord) {
		DefaultSVNOptions options = new DefaultSVNOptions();
		manager = SVNClientManager.newInstance(options);
		manager = SVNClientManager.newInstance(options, userName, passWord);
		updateClient = manager.getUpdateClient();
		updateClient.setIgnoreExternals(false);
	}

	/**
	 * 获取文档内容
	 * @param url
	 * @return
	 */
	public String checkoutFileToString(String url) throws SVNException {
		//"", -1, null
		SVNRepository repository = createRepository(url);
		SVNDirEntry entry = repository.getDir("", -1, false, null);
		int size = (int) entry.getSize();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(size);
		SVNProperties properties = new SVNProperties();
		repository.getFile("", -1, properties, outputStream);
		String doc = new String(outputStream.toByteArray(), Charset.forName("utf-8"));
		return doc;
	}

	public boolean toParantFolder() {
		if (url != null) {
			StringBuffer sb = new StringBuffer(url);
			if (url.endsWith("/")) {
				sb.deleteCharAt(sb.length() - 1);
			}
			int index = sb.lastIndexOf("/");
			url = sb.substring(0, index);
			return true;
		}
		return false;
	}

	/**
	 * 进入子目录
	 *
	 * @param folder
	 * @return
	 */
	public boolean toChildFolder(String folder) {
		if (url != null) {
			StringBuffer sb = new StringBuffer(url);
			boolean a = url.endsWith("/");
			boolean b = folder.startsWith("/");
			if (a ^ b) {
				sb.append(folder);
			} else if (a & b) {
				sb.deleteCharAt(sb.length() - 1);
				sb.append(folder);
			} else {
				sb.append('/').append(folder);
			}
			if (checkPath(sb.toString()) == 1) {
				this.url = sb.toString();
				return true;
			}
		}
		return false;
	}


	/**
	 * 获取当前目录下的子目录和文件
	 *
	 * @return
	 * @throws SVNException
	 */
	public List<SVNDirEntry> listFolder() throws SVNException {
		return listFolder(url);
	}

	/**
	 * 列出指定SVN 地址目录下的子目录
	 *
	 * @param url
	 * @return
	 * @throws SVNException
	 */
	public List<SVNDirEntry> listFolder(String url) {
		if (checkPath(url) == 1) {

			SVNRepository repository = createRepository(url);
			try {
				Collection<SVNDirEntry> list = repository.getDir("", -1, null, (List<SVNDirEntry>) null);
				List<SVNDirEntry> dirs = new ArrayList<SVNDirEntry>(list.size());
				dirs.addAll(list);
				return dirs;
			} catch (SVNException e) {
				logger.error("listFolder error", e);
			}

		}
		return null;
	}

	private SVNRepository createRepository(String url) {

		try {
			return manager.createRepository(SVNURL.parseURIEncoded(url), true);
		} catch (SVNException e) {
			logger.error("createRepository error", e);
		}
		return null;
	}

	/**
	 * 检查路径是否存在
	 *
	 * @param url
	 * @return 1：存在    0：不存在   -1：出错
	 */
	public int checkPath(String url) {
		SVNRepository repository = createRepository(url);
		SVNNodeKind nodeKind;
		try {
			nodeKind = repository.checkPath("", -1);
			boolean result = nodeKind == SVNNodeKind.NONE ? false : true;
			if (result) {
				return 1;
			}
		} catch (SVNException e) {
			logger.error("checkPath error", e);
			return -1;
		}
		return 0;
	}


	/**
	 * 从远程获取文件
	 * @param url
	 * @param targetDirPath
	 * @return
	 */
	public boolean getFileFromSVN(String url,String targetDirPath){
		File targetDir =new File(targetDirPath);
		SVNURL repositoryURL = null;
		try {
			repositoryURL = SVNURL.parseURIEncoded(url);
		} catch (SVNException e) {
			logger.debug("errorStack:", e);
			return false;
		}
		updateClient.setIgnoreExternals(false);
		// 执行check out 操作，返回工作副本的版本号。
		long workingVersion = -1;
		try {
			workingVersion = updateClient.doCheckout(repositoryURL, targetDir, SVNRevision.HEAD, SVNRevision.HEAD,
					SVNDepth.INFINITY, false);
		} catch (SVNException e) {
			logger.debug("errorStack:", e);
			return false;
		} catch (Exception e) {
			logger.debug("errorStack:", e);
			return false;
		}

		return true;
	}

	public static void main(String[] args) throws SVNException {
		String filePath = SvnUtil.class.getClassLoader().getResource("XSBANK_核心版本_送测清单_20220315.xlsx").getFile();

		System.out.println(filePath);

		/*String url = "svn://10.100.3.59/hexin/doc/trunk/02 锡商核心二期/13 环境管理/05 版本送测/01 核心送测清单";
		SvnUtil svn = new SvnUtil("dcits_pansongwei", "password");
		List<SVNDirEntry> list = svn.listFolder(url);
		String path="C:\\Users\\pansw\\Desktop\\test";
		for (SVNDirEntry svnDirEntry : list) {
			if ("XSBANK_核心版本_送测清单_20220315.xlsx".equals(svnDirEntry.getName())) {
			//	url = url + "/" + svnDirEntry.getName();
				svn.getFileFromSVN(url,path);
			}
		}*/
	}


}
