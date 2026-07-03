package io.github.guilhermepagio.ms.springcloud.hroauth.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Role {

    private Long id;
    private String roleName;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Role role = (Role) o;

        return roleName != null ? roleName.equals(role.roleName) : role.roleName == null;
    }

    @Override
    public int hashCode() {
        return roleName != null ? roleName.hashCode() : 0;
    }
}
