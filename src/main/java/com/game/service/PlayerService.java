package com.game.service;

import com.game.entity.Player;
import com.game.exception.BadRequestException;
import com.game.exception.NotFoundRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PlayerService {

    /**
     * Поиск по полям name и title происходить по частичному
     * соответствию. Например, если в БД есть игрок с именем
     * «Камираж», а параметр name задан как «ир» - такой игрок
     * должен отображаться в результатах (Камираж).
     * pageNumber – параметр, который отвечает за номер
     * отображаемой страницы при использовании пейджинга.
     * Нумерация начинается с нуля
     * pageSize – параметр, который отвечает за количество
     * результатов на одной странице при пейджинге
     *
     * @param specification Настройки спецификации поиска
     * @param pageable      Настройки пейджинга поиска
     * @return результатов на одной странице при пейджинге
     */
    Iterable<Player> getPlayers(Specification<Player> specification, Pageable pageable);

    /**
     * Получить количество игроков
     *
     * @param specification Настройки спецификации поиска
     * @return Количество игроков
     */
    Integer getPlayersCount(Specification<Player> specification);

    /**
     * Создание игрока и сохранение в БД
     * Мы не можем создать игрока, если:
     * - указаны не все параметры из Data Params (кроме banned);
     * - длина значения параметра “name” или “title” превышает размер соответствующего поля в БД (12 и 30 символов);
     * - значение параметра “name” пустая строка;
     * - опыт находится вне заданных пределов;
     * - “birthday”:[Long] < 0;
     * - дата регистрации находятся вне заданных пределов.
     * В случае всего вышеперечисленного необходимо ответить ошибкой с кодом 400
     *
     * @param player Параметры игрока
     * @return Данный сохраненного игрока
     */
    Player createPlayer(Player player);

    /**
     * Получить игрока по ID
     *
     * @param id Идентификатор игрока
     * @return Игрок из базы данных после сохранения
     * @throws BadRequestException      400 - Значение id не валидное
     * @throws NotFoundRequestException 404 - Игрок не найден в БД
     */
    Player getPlayerById(Long id);

    /**
     * Обновление игрока по ID.
     * Обновлять нужно только те поля, которые не null
     *
     * @param id     Идентификатор игрока
     * @param player Параметры игрока
     * @return Игрок из базы данных после обновления
     * @throws BadRequestException      400 - Значение id не валидное
     * @throws NotFoundRequestException 404 - Игрок не найден в БД
     */
    Player updatePlayer(Long id, Player player);

    /**
     * Удаление игрока по ID
     *
     * @param id Идентификатор игрока
     * @throws BadRequestException      400 - Значение id не валидное
     * @throws NotFoundRequestException 404 - Игрок не найден в БД
     */
    void deletePlayer(Long id);
}
