package io.yorosoft.ebanking.service.UserServiceImpl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.yorosoft.ebanking.dao.UserDao;
import org.springframework.security.core.userdetails.User;

import org.slf4j.Logger;

/**
 * UserSecurityService
 */
@Service
public class UserSecurityService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);

    private UserDao userDao;

    @Autowired
    public UserSecurityService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(null == user) {
            LOG.warn("Nom Utilisateur {} introuvable", username);
            throw new UsernameNotFoundException("Nom Utilisateur "+ username + " introuvable");
        } 
        return user;
    }

}