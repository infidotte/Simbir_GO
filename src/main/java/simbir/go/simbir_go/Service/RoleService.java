package simbir.go.simbir_go.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import simbir.go.simbir_go.Entity.Role;
import simbir.go.simbir_go.Repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
        Role user = createIfNotExists("ROLE_USER");
        Role admin = createIfNotExists("ROLE_ADMIN");
    }

    public Role findByName(String name){
        return repository.findByName(name).orElse(null);
    }

    public Role save(Role role){
        return repository.save(role);
    }

    private Role createIfNotExists(String name){
        if(!repository.existsByName(name)){
            Role role = new Role();
            role.setName(name);
            return save(role);
        }else{
            return findByName(name);
        }
    }
}
