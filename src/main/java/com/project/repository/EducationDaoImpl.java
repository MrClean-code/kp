package com.project.repository;

import com.project.db.ConnectionDB;
import com.project.entity.Education;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EducationDaoImpl implements EducationDao {
    private final ConnectionDB connectionDB;
    private final Connection connection;

    {
        connectionDB = new ConnectionDB();
        connection = connectionDB.getConnection();
    }

    @Override
    public List<Education> findDataTwoParam(String param1, String param2) {
        List<Education> educationList = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM EDUCATION e" +
                    " WHERE (e.specialization = ? and e.middleScoreExam = ?) or" +
                    " (e.specialization = ? and e.middleScoreDiploma = ?)");
            ps.setString(1, param1);
            ps.setString(2, param2);
            ps.setString(3, param1);
            ps.setString(4, param2);
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

        return educationList;
    }

    @Override
    public List<Education> findDataOneParam(String param) {
        List<Education> educationList = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM EDUCATION WHERE specialization = ? OR" +
                    " middleScoreExam = ? OR " +
                    " middleScoreDiploma = ?");
            ps.setString(1, param);
            ps.setString(2, param);
            ps.setString(3, param);
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
}
