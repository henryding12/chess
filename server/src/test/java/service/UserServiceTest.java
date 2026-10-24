package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;

public class UserServiceTest {
    static final MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    static final MemoryGameDAO memoryGameDAO = new MemoryGameDAO();
    static final MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();

    static final AdminService adminService = new AdminService(memoryUserDAO, memoryGameDAO, memoryAuthDAO);
    static final UserService userService = new UserService(memoryUserDAO, memoryAuthDAO);

}

