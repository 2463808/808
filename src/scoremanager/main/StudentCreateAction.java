package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class StudentCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session =req.getSession();//セッション

		Teacher teacher = (Teacher)session.getAttribute("user");
		String entYearStr="";//入力された入学年度
		String classNum="";//入力されたクラス番号
		int entYear = 0;// 入学年度
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		LocalDate todaysDate = LocalDate.now();// LocalDateインスタンスを取得
		int year = todaysDate.getYear();//現在の年を取得

		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");

		//DBからデータ取得 3
		//ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());


		//ビジネスロジック 4
		if (entYearStr != null) {
			//数値に変換
			entYear = Integer.parseInt(entYearStr);
		}

		// リストを初期化
		List<Integer>entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++){
			entYearSet.add(i);
		}

		//DBへデータ保存 5


		//レスポンス値をセット 6
		req.setAttribute("f1",entYear);
		// リクエストにクラス番号をセット
		req.setAttribute("f2", classNum);
		//リクエストにデータをセット
		req.setAttribute("class_num_set",list);
		req.setAttribute("ent_year_set",entYearSet);

		//JSPへフォワード 7
		req.getRequestDispatcher("student_create.jsp").forward(req, res);
	}
}
