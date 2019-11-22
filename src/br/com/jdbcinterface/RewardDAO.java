package br.com.jdbcinterface;

import java.util.List;

import br.com.objects.Reward;

public interface RewardDAO
{
	public List<Reward> searchRewards();
}