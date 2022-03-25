package com.example.demo.Git.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.gitlab4j.api.models.Diff;

import java.util.List;

/**
 * The <code>com.example.demo.Git.Model.CompareCommitsVo</code> class have to be described
 * <p>
 * The <code>CompareCommitsVo</code> class have to be detailed For example:
 * <p>
 *
 * @author pansw
 * @date 2022/2/24 17:51
 * @see
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class CompareCommitsVo {
	private Commit commitBean;
	private List<Commit> commitsBeanLise;
	private List<Diff> diffs;
	private String compare_timeout;
	private String compare_same_ref;

	@NoArgsConstructor
	@Data
	public static class Diff {
        private String old_path ;
		private String new_path;
		private String a_mode;
		private String b_mode;
		private String diff;
		private String new_file;
		private String renamed_file;
		private String deleted_file;
	}
	@NoArgsConstructor
	@Data
	public static class Commit {
		private String id;
		private String short_id;
		private String title;
		private String author_name;
		private String author_email;
		private String created_at;

	}
/*	@NoArgsConstructor
	@Data
	public static  class  CommitsBean{
		private String id;
		private String short_id;
		private String title;
		private String author_name;
		private String author_email;
		private String created_at;
	}*/
}
