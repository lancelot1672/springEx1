package com.example.spring.member.repository;

import com.example.spring.member.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{
    private final DataSource dataSource;

    // 주입 - DataSource를 생성해놓고 주입시켜준다.
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            connection = getConnection();
            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,member.getName());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            if(rs.next()){
                member.setId(rs.getLong(1));
            }else{
                throw new SQLException("id 조회 실패");
            }
            return member;
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally {
            //자원 반납해주어야함
            close(connection, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();
            if(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }else{
                return Optional.empty();
            }
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally {
            //자원 반납해주어야함
            close(connection, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();
            if(rs.next()){
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }else{
                return Optional.empty();
            }
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally {
            //자원 반납해주어야함
            close(connection, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);

            List<Member> members= new ArrayList<>();

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }

            return members;
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally {
            //자원 반납해주어야함
            close(connection, pstmt, rs);
        }
    }
    // DataSource 커넥션 얻기
    // 데이터베이스 커넥션을 유지를 해줌.
    private Connection getConnection(){
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
