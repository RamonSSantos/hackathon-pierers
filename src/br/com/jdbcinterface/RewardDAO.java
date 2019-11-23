package br.com.jdbcinterface;

import java.sql.SQLException;
import java.util.List;

import br.com.objects.Reward;
import br.com.objects.User;

public interface RewardDAO
{
	public List<Reward> searchRewards() throws SQLException;
	public List<Reward> getQuantityReceived(int idUser, List<Reward> rewardList) throws SQLException;
	public User getCurrentScoreUser(int idUser) throws SQLException;
	public boolean getReward(User user, int idReward) throws SQLException;
	public boolean insertRewardsByUser(int idUser, int idReward) throws SQLException;
}