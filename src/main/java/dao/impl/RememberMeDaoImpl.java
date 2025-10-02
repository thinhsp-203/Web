package dao.impl;

import dao.RememberMeDao;
import model.RememberMeToken;
import util.DBConnection;

import java.sql.*;
import java.time.Instant;

public class RememberMeDaoImpl implements RememberMeDao {

    @Override
    public void save(RememberMeToken t) throws Exception {
        String sql = "INSERT INTO remember_me_tokens (user_id, selector, validator_hash, expires_at) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, t.getUserId());
            ps.setString(2, t.getSelector());
            ps.setString(3, t.getValidatorHash());
            ps.setTimestamp(4, Timestamp.from(t.getExpiresAt()));
            ps.executeUpdate();
        }
    }

    @Override
    public RememberMeToken findBySelector(String selector) throws Exception {
        String sql = "SELECT user_id, selector, validator_hash, expires_at FROM remember_me_tokens WHERE selector = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, selector);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                RememberMeToken t = new RememberMeToken();
                t.setUserId(rs.getLong("user_id"));
                t.setSelector(rs.getString("selector"));
                t.setValidatorHash(rs.getString("validator_hash"));
                Timestamp ts = rs.getTimestamp("expires_at");
                t.setExpiresAt(ts != null ? ts.toInstant() : Instant.EPOCH);
                return t;
            }
        }
    }

    @Override
    public void deleteBySelector(String selector) throws Exception {
        String sql = "DELETE FROM remember_me_tokens WHERE selector = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, selector);
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteAllForUser(long userId) throws Exception {
        String sql = "DELETE FROM remember_me_tokens WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.executeUpdate();
        }
    }
}
