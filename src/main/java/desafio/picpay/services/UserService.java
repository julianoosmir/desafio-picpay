package desafio.picpay.services;

import desafio.picpay.domain.user.User;
import desafio.picpay.domain.user.UserDTO;
import desafio.picpay.domain.user.UserType;
import desafio.picpay.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    public User save(UserDTO userdto) {
        User user = this.mapper.map(userdto, User.class);
        return userRepository.save(user);
    };
    public User save(User user) {
        return userRepository.save(user);
    };
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
    public User findUserById(Long id) throws Exception {
        return this.userRepository.findById(id).orElseThrow(()-> new Exception("Usuario não encontrado"));
    }

    public void valdateUser(User payer, BigDecimal amout) throws Exception {
        if (payer.getUserType() == UserType.MERCHANT){
            throw new Exception("Um usuario Logista não pode realizar transação");
        }
        if(payer.getBalance().compareTo(amout) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

}
