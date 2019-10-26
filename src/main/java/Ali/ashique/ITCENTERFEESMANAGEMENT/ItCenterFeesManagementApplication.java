package Ali.ashique.ITCENTERFEESMANAGEMENT;

import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class ItCenterFeesManagementApplication {

    private static UserRepo userRepo;

    public static void main(String... args) {
        System.out.println(Arrays.toString(args));
        SpringApplication.run(ItCenterFeesManagementApplication.class, args);


        //List<Integer> nums= Arrays.asList(1,2,3,4,5,3);
//		List<Integer> numsNoDuplicate=new LinkedList<>(new HashSet<>(nums));
//		//numsNoDuplicate.forEach(i-> System.out.println(i));
//		List<Integer> collect = numsNoDuplicate.stream().distinct().collect(Collectors.toList());
        //collect.forEach(i-> System.out.println(i));
//		Stream<String> stringStream=Stream.of("Tea","Apple","Mango");
////		System.out.println(stringStream.anyMatch(i -> i.contains("e")));;
////		stringStream.close();
//		System.out.println(stringStream.parallel().allMatch(i-> i.contains("o"))+"all match");;
//		Stream<String> stringStream1 = stringStream.parallel().filter(i -> i.contains("e"));
//		stringStream.parallel().forEach(i-> System.out.println(i));
//		nums.stream().filter(i->i%2==0).forEach(i-> System.out.println(i));
//		nums=nums.stream().map(i->i+100).collect(Collectors.toList());
//		nums.forEach(i-> System.out.println(i));
    }

}
