package com.bikkadit.electronicstore.electronicstore.service;

import com.bikkadit.electronicstore.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.electronicstore.dtos.ProductDto;
import com.bikkadit.electronicstore.electronicstore.entities.Product;
import com.bikkadit.electronicstore.electronicstore.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    Product product;

    @BeforeEach
    public void init(){
        product=Product.builder()
                .title("iPhone")
                .description("It s very expensive mobile")
                .price(150000)
                .discountedPrice(120000)
                .quantity(20)
                .live(true)
                .stock(true)
                .build();
    }

    // create product test
    @Test
    public void createProductTest(){

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = productService.create(mapper.map(product, ProductDto.class));
        System.out.println(productDto.getTitle());
        Assertions.assertEquals("iPhone",productDto.getTitle());
    }

    // update product test
    @Test
    public void updateProductTest(){

        String productId="dtevdrwr4";
        ProductDto productDto = ProductDto.builder()
                .title("iPhone 13 pro")
                .description("It s very expensive mobile")
                .price(160000)
                .discountedPrice(130000)
                .quantity(20)
                .live(true)
                .stock(true)
                .build();

        Mockito.when(productRepository.findById("dtevdrwr4")).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto updateProduct = productService.update(productDto, productId);
        System.out.println(updateProduct.getTitle());
        Assertions.assertEquals(productDto.getTitle(),updateProduct.getTitle(),"title not matched...");
    }

    // delete product test
    @Test
    public void deleteProductTest(){
        String productId="43grf43r";
        Mockito.when(productRepository.findById("43grf43r")).thenReturn(Optional.of(product));
        productService.delete(productId);
        Mockito.verify(productRepository,Mockito.times(1)).delete(product);
    }

    // get single product id test
    @Test
    public void getSingleProductIdTest(){
        String productId="prov454df5";
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ProductDto singleId = productService.get(productId);
        System.out.println(singleId.getTitle());
        Assertions.assertEquals(product.getTitle(),singleId.getTitle(),"product name not matched!!");
    }

    @Test
    public void getAllProductTest(){
        Product product1=Product.builder()
                .title("iPhone 13 pro")
                .description("It s very expensive mobile")
                .price(130000)
                .discountedPrice(110000)
                .quantity(10)
                .live(true)
                .stock(true)
                .build();

        Product  product2=Product.builder()
                .title("iPhone 14 pro max")
                .description("It s very expensive mobile")
                .price(160000)
                .discountedPrice(140000)
                .quantity(15)
                .live(true)
                .stock(true)
                .build();

        List<Product> productList = Arrays.asList(product, product1, product2);
        Page<Product> page=new PageImpl<>(productList);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> allproducts = productService.getAll(1, 2, "title", "asc");
        System.out.println(allproducts.getTotalElements());
        Assertions.assertEquals(3,allproducts.getContent().size());


    }

    @Test
    public void getAllLiveProductTest(){
        Product product1=Product.builder()
                .title("iPhone 13 pro")
                .description("It s very expensive mobile")
                .price(130000)
                .discountedPrice(110000)
                .quantity(10)
                .live(true)
                .stock(true)
                .build();

        Product  product2=Product.builder()
                .title("iPhone 14 pro max")
                .description("It s very expensive mobile")
                .price(160000)
                .discountedPrice(140000)
                .quantity(15)
                .live(false)
                .stock(true)
                .build();
        List<Product> productList = Arrays.asList(product, product1);
        Page<Product> page=new PageImpl<>(productList);
        Mockito.when(productRepository.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> allLive = productService.getAllLive(1, 2, "title", "asc");
        System.out.println(allLive.getTotalElements());
        Assertions.assertEquals(2,allLive.getContent().size());
    }


}
