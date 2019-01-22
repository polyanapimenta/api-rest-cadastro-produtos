package br.com.loja.cadastroprodutos.swagger;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	@Autowired
	private TypeResolver resolver;
	
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
			    .additionalModels(resolver.resolve(CustomTypeForProducts.class))
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.loja.cadastroprodutos"))
				.paths(regex("/products.*"))
				.build()
				.apiInfo(metaData());
	}

	private ApiInfo metaData() {		    
	    return new ApiInfoBuilder()
	            .title("API REST Loja.com.br")
	            .description("Aplicação para cadastro de produtos, alteração de estoque, ou remoção do mesmo.")
	            .version("1.0.0")
	            .build();
	}
}
