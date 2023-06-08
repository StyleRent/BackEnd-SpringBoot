package kr.stylerent.StyleRent.service;

import kr.stylerent.StyleRent.repository.ProductRepository;
import kr.stylerent.StyleRent.repository.RentRepository;
import kr.stylerent.StyleRent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RentRepository rentRepository;

    public
}
