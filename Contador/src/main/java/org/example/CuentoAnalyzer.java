package org.example;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class CuentoAnalyzer {

    public static void main(String[] args) throws Exception {
        // Leer el cuento de un archivo de texto
        String cuento = new String(Files.readAllBytes(Paths.get("C:\\Users\\franc\\IdeaProjects\\TOMAS EL PAPU.txt")));

        // Patrón para dividir las oraciones que empiezan con mayúscula y terminan con punto
        Pattern sentencePattern = Pattern.compile("[A-Z][^.]+\\.");

        // Dividir el cuento en frases usando stream
        List<String> sentences = sentencePattern.matcher(cuento).results()
                .map(MatchResult::group)
                .collect(Collectors.toList());

        // Total de letras en todo el cuento
        long totalLetters = cuento.chars()
                .filter(Character::isLetter)
                .count();

        System.out.println("Total de letras en el cuento: " + totalLetters);

        // Procesar cada oración
        IntStream.range(0, sentences.size()).forEach(i -> {
            String sentence = sentences.get(i);

            // Dividir la oración en palabras
            List<String> words = Arrays.stream(sentence.split("\\s+"))
                    .map(word -> word.replaceAll("[^a-zA-Z]", "")) // Eliminar puntuación
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.toList());

            // Contar palabras por longitud
            Map<Integer, Long> wordLengthCount = words.stream()
                    .collect(Collectors.groupingBy(String::length, Collectors.counting()));

            System.out.println("En la frase " + (i + 1) + " hay:");
            wordLengthCount.forEach((length, count) ->
                    System.out.println(count + " palabras de " + length + " letras"));
        });
    }
}
