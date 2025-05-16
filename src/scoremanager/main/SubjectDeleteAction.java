package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // リクエストパラメータ（cd）を取得
        String cd = req.getParameter("cd");

        //  DAOを使って対象のSubjectを取得
        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(cd);

        // 取得したSubjectをリクエストスコープにセット
        req.setAttribute("subject", subject);

        // subject_delete.jsp にフォワード
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
