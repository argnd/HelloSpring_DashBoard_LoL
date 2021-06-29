package com.hello.spring.service;

import com.hello.spring.model.Hero;
import com.hello.spring.model.Team;
import com.hello.spring.model.User;
import com.hello.spring.repository.HeroRepository;
import com.hello.spring.repository.TeamRepository;
import com.hello.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class ManageTeamService {
    private static final int DUMMY_HERO_ID = 1;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final HeroRepository heroRepository;

    @Autowired
    public ManageTeamService(UserRepository userRepository, TeamRepository teamRepository, HeroRepository heroRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.heroRepository = heroRepository;
    }

    public List<Hero> getUserHeroes(User user){
        int userid = user.getId();
        return user.getTeam().getHeroes();
    }

    public List<Hero> getAllHeroes(){
        return heroRepository.findAll();
    }

    public Optional<Hero> getById(Integer id){
        return heroRepository.findById(id); //find return optional
    }

    public void addHeroToUser(Integer heroId, HttpSession session){
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();

        Hero heroToAdd = heroRepository.getById(heroId); //get reutrn not optional

        Team currentTeam = user.getTeam();
        List<Hero> teamHeroes = currentTeam.getHeroes();

        addHeroToFirstEmptySlotInTeam(teamHeroes, heroToAdd);
        userRepository.save(user);
        session.removeAttribute("user");
        session.setAttribute("user", user);
    }


    private void addHeroToFirstEmptySlotInTeam(List<Hero> team, Hero hero){
        for (int i = 0; i < team.size(); i++) {
            if(team.get(i).getId() == DUMMY_HERO_ID){
                team.set(i, hero);
                break;
            }
        }
    }

}
