package com.project.repository;

import com.project.db.ConnectionDB;
import com.project.entity.Education;
import com.project.parse.ParseEducation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrudDaoImpl implements CrudDao {

    private final ConnectionDB connectionDB;
    private final Connection connection;
    private ParseEducation parseEducation;
    private List<Education> educationList;

    {
        connectionDB = new ConnectionDB();
        connection = connectionDB.getConnection();
    }

    @Override
    public void saveData(List<Education> educationList) {
        parseEducation = new ParseEducation();
        PreparedStatement ps = null;
        for (Education education : educationList) {
            try {
                ps = connection.prepareStatement("insert into education (code, " +
                        " specialization, level, " +
                        " faculty, middlescoreexam, middlescorediploma)" +
                        " VALUES (?, ?, ?, ?, ?, ?)");
                ps.setString(1, education.getCode());
                ps.setString(2, education.getSpecialization());
                ps.setString(3, education.getLevel());
                ps.setString(4, education.getFaculty());
                ps.setString(5, education.getMiddleScoreExam());
                ps.setString(6, education.getMiddleScoreDiploma());

                ps.executeUpdate();
                System.out.println("DB add " + education.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public List<Education> getData() {
        educationList = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM education");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Education education = new Education();

                education.setId(rs.getInt("id"));
                education.setCode(rs.getString("code"));
                education.setFaculty(rs.getString("faculty"));
                education.setLevel(rs.getString("level"));
                education.setSpecialization(rs.getString("specialization"));
                education.setMiddleScoreExam(rs.getString("middleScoreExam"));
                education.setMiddleScoreDiploma(rs.getString("middleScoreDiploma"));

                educationList.add(education);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        educationList.toString();
        return educationList;
    }

    @Override
    public void updateDate(List<Education> listUpdate) {
        PreparedStatement ps = null;
        for (Education education: listUpdate) {
            try {
                ps = connection.prepareStatement("UPDATE education SET code = ?," +
                        " specialization = ?, level = ?, faculty = ?, middlescorediploma = ? " +
                        " middlescoreexam = ? WHERE id = ? ");
                ps.setString(1, education.getCode());
                ps.setString(2, education.getSpecialization());
                ps.setString(3, education.getLevel());
                ps.setString(4, education.getFaculty());
                ps.setString(5, education.getMiddleScoreExam());
                ps.setString(6, education.getMiddleScoreDiploma());
                ps.setInt(7, education.getId());

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void deleteData(List<Education> listDelete) {
        PreparedStatement ps = null;
        for (Education education: listDelete) {
            try {
                ps = connection.prepareStatement("DELETE FROM education WHERE id = ? ");
                ps.setInt(1, education.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
