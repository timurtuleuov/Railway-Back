package tuleuov.space.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuleuov.space.railway.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
