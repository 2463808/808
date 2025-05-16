package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		/*HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");*/
		String cd = ""; // 学生番号
		String name= ""; // 氏名
		Subject subject = new Subject();
		SubjectDao subjectDao = new SubjectDao();

		// リクエストパラメーターの取得 2
		cd = req.getParameter("cd");

		// DBからデータ取得 3
		// 学生の詳細データを取得
		subject = subjectDao.get(cd);

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得

		// ビジネスロジック 4
		cd = subject.getCd();
		name = subject.getName();

		// レスポンス値をセット 6
		// リクエストに入学年度をセット
		req.setAttribute("cd", cd);
		// リクエストに氏名をセット
		req.setAttribute("name", name);
		req.setAttribute("subject", subject);


		// JSPへフォワード 7
		req.getRequestDispatcher("subject_update.jsp").forward(req, res);
	}

}
