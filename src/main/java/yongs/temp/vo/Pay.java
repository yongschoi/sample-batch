package yongs.temp.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Pay {
    // private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private Long id;
    private Long amount;
    private String txName;
    private LocalDateTime txDateTime;

    public Pay(Long amount, String txName, LocalDateTime txDateTime) {
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = txDateTime;
        // this.txDateTime = LocalDateTime.parse(txDateTime, FORMATTER);
    }

    public Pay(Long id, Long amount, String txName, LocalDateTime txDateTime) {
        this.id = id;
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = txDateTime;
        // this.txDateTime = LocalDateTime.parse(txDateTime, FORMATTER);
    }
}
