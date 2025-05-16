package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // DAO生成
        SubjectDao subjectDao = new SubjectDao();

        //  リクエストパラメータから削除対象の科目コードを取得
        String cd = req.getParameter("cd");

        // 対象のSubjectを取得
        Subject subject = subjectDao.get(cd);

        // 削除処理の実行
        subjectDao.delete(subject);

        // 完了画面へリダイレクト
        res.sendRedirect("subject_delete_done.jsp");
    }
}
