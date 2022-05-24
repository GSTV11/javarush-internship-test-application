package com.game.service.impl;

import com.game.entity.Player;
import com.game.exception.BadRequestException;
import com.game.exception.NotFoundRequestException;
import com.game.repository.PlayerRepository;
import com.game.service.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Iterable<Player> getPlayers(Specification<Player> specification, Pageable pageable) {
        Page<Player> playerPage = playerRepository.findAll(specification, pageable);
        return playerPage.getContent();
    }

    @Override
    public Integer getPlayersCount(Specification<Player> specification) {
        long count = playerRepository.count(specification);
        return Math.toIntExact(count);
    }

    @Override
    public Player createPlayer(Player player) {

        if (!isValidPlayer(player)) throw new BadRequestException("Data params not valid");

        if (player.getId() != null)
            player.setId(null);

        if (player.getBanned() == null)
            player.setBanned(false);

        calculateAndUpdateLevelAndUntilNextLevel(player);

        return playerRepository.save(player);
    }

    @Override
    public Player getPlayerById(Long id) {
        if (id <= 0) throw new BadRequestException("ID is not valid");

        Optional<Player> player = playerRepository.findById(id);

        if (!player.isPresent()) throw new NotFoundRequestException("Player not found to database");

        return player.get();
    }

    @Override
    public Player updatePlayer(Long id, Player player) {
        Player playerEntity = getPlayerById(id);

        if (player.getName() != null) playerEntity.setName(player.getName());
        if (player.getTitle() != null) playerEntity.setTitle(player.getTitle());
        if (player.getRace() != null) playerEntity.setRace(player.getRace());
        if (player.getProfession() != null) playerEntity.setProfession(player.getProfession());
        if (player.getBirthday() != null) {
            if (!isValidDate(player.getBirthday())) throw new BadRequestException("Birthday is not valid");
            playerEntity.setBirthday(player.getBirthday());
        }
        if (player.getBanned() != null) playerEntity.setBanned(player.getBanned());
        if (player.getExperience() != null) {
            if (!isValidExperience(player.getExperience())) throw new BadRequestException("Experience is not valid");
            playerEntity.setExperience(player.getExperience());
            calculateAndUpdateLevelAndUntilNextLevel(playerEntity);
        }

        return playerRepository.save(playerEntity);
    }

    @Override
    public void deletePlayer(Long id) {
        if (id <= 0) throw new BadRequestException("ID is not valid");

        Player playerEntity = getPlayerById(id);
        playerRepository.delete(playerEntity);
    }

    private boolean isValidPlayer(Player player) {
        return player != null &&
                player.getName() != null &&
                player.getTitle() != null &&
                player.getRace() != null &&
                player.getProfession() != null &&
                player.getBirthday() != null &&
                isValidExperience(player.getExperience()) &&
                !player.getName().trim().isEmpty() &&
                player.getName().length() <= 12 &&
                player.getTitle().length() <= 30;
    }

    private boolean isValidExperience(Integer experience) {
        return experience != null && experience > 0 && experience <= 10000000;
    }

    private boolean isValidDate(Date date) {
        return date != null && date.getTime() >= 0;
    }

    /**
     * Метод обновляет текущий уровень персонажа и опыт необходимый для достижения следующего уровня
     *
     * @param player Параметры игрока
     */
    private void calculateAndUpdateLevelAndUntilNextLevel(Player player) {
        int level = (int) (Math.sqrt(2500 + (200 * player.getExperience())) - 50) / 100;
        int untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);
    }
}
