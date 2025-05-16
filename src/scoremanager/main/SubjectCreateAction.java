package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import dao.SchoolDao;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        //  セッションからログインユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");  // ログイン時に user を格納している前提

        if (teacher == null) {
            // ログインしていない場合はログイン画面にリダイレクト
            res.sendRedirect("Login.action");
            return;
        }

        // ログインユーザーの学校コードを取得
        String schoolCd = teacher.getSchool().getCd();

        // 学校情報を取得
        SchoolDao schoolDao = new SchoolDao();
        School school = schoolDao.get(schoolCd);

        // JSPへ渡す
        req.setAttribute("school", school);

        // subject_create.jsp にフォワード
        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
}
