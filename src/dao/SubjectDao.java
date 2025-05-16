package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {
	// private String baseSql = "select * from student where school_cd=? ";

	public List<Subject> getAll() throws Exception {
	    List<Subject> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rSet = null;

	    try {
	        String sql = "SELECT * FROM subject ORDER BY cd ASC";
	        statement = connection.prepareStatement(sql);
	        rSet = statement.executeQuery();

	        SchoolDao schoolDao = new SchoolDao();

	        while (rSet.next()) {
	            Subject subject = new Subject();
	            subject.setCd(rSet.getString("cd"));
	            subject.setName(rSet.getString("name"));
	            subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
	            list.add(subject);
	        }
	    } finally {
	        if (rSet != null) rSet.close();
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    return list;
	}



	public Subject get(String cd) throws Exception {
	    Subject subject = null;
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rSet = null;

	    try {
	        statement = connection.prepareStatement("SELECT * FROM subject WHERE cd = ?");
	        statement.setString(1, cd);
	        rSet = statement.executeQuery();

	        if (rSet.next()) {
	            subject = new Subject();
	            subject.setCd(rSet.getString("cd"));
	            subject.setName(rSet.getString("name"));

	            // 学校情報を取得してセット
	            SchoolDao schoolDao = new SchoolDao();
	            subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (rSet != null) rSet.close();
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    return subject;
	}



    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            // SQL文：特定の学校コードに紐づく科目一覧を取得
            String sql = "SELECT * FROM subject WHERE school_cd = ? ORDER BY cd ASC";
            statement = connection.prepareStatement(sql);
            statement.setString(1, school.getCd());
            rSet = statement.executeQuery();

            while (rSet.next()) {
                Subject subject = new Subject();
                subject.setCd(rSet.getString("cd"));
                subject.setName(rSet.getString("name"));
                subject.setSchool(school);  // schoolオブジェクトは引数から流用
                list.add(subject);
            }

        } finally {
            if (rSet != null) rSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }


public boolean save(Subject subject) throws Exception {
	// コネクションを確立
	Connection connection = getConnection();
	// プリペアードステートメント
	PreparedStatement statement = null;
	// 実行件数
	int count = 0;

	try {
		// データベースから学生を取得
		Subject old = get(subject.getCd());
		if (old == null) {
			// 学生が存在しなかった場合
			// プリペアードステートメントにINSERT文をセット
			statement = connection.prepareStatement("insert into subject(cd, name, school_cd) values(?, ?, ?)");
			// プリペアードステートメントに値をバインド
			statement.setString(1, subject.getCd());
			statement.setString(2, subject.getName());
		    statement.setString(3, subject.getSchool().getCd());


		}else {
			// 学生が存在した場合
			// プリペアードステートメントにUPDATE文をセット
			statement = connection.prepareStatement("update subject set name=?, school_cd=? where cd=?");
			statement.setString(1, subject.getName());
			statement.setString(2, subject.getSchool().getCd());
			statement.setString(3, subject.getCd());

		}

		// プリペアードステートメントを実行
		count = statement.executeUpdate();
	}catch (Exception e) {
		throw e;
	}finally {
		// プリペアードステートメントを閉じる
		if (statement != null) {
			try {
				statement.close();
			}catch (SQLException sqle) {
				throw sqle;
			}
		}
		if (connection != null) {
			try {
				connection.close();
			}catch (SQLException sqle) {
				throw sqle;
			}
		}
	}
	// 実行件数が1件以上ある場合
	if (count > 0) {
		return true;
	} else
		// 実行件数が0件の場合
		return false;
	}



public boolean delete(Subject subject) throws Exception {
	// コネクションを確立
	Connection connection = getConnection();
	// プリペアードステートメント
	PreparedStatement statement = null;
	// 実行件数
	int count = 0;

	try {
		// データベースから学生を取得
			// プリペアードステートメントにUPDATE文をセット
			statement = connection.prepareStatement("delete from subject where cd=?");
			statement.setString(1, subject.getCd());

		// プリペアードステートメントを実行
		count = statement.executeUpdate();
	}catch (Exception e) {
		throw e;
	}finally {
		// プリペアードステートメントを閉じる
		if (statement != null) {
			try {
				statement.close();
			}catch (SQLException sqle) {
				throw sqle;
			}
		}
		if (connection != null) {
			try {
				connection.close();
			}catch (SQLException sqle) {
				throw sqle;
			}
		}
	}
	//  実行件数が1件以上ある場合
	if (count > 0) {
		return true;
	} else
		// 実行件数が0件の場合
		return false;
	}
}
