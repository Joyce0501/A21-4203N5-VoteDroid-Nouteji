package com.example.votedroid.service;

import com.example.votedroid.bd.BD;
import com.example.votedroid.exceptions.MauvaisVote;
import com.example.votedroid.exceptions.MauvaiseQuestion;
import com.example.votedroid.modele.VDQuestion;
import com.example.votedroid.modele.VDVote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceImplementation {

    private static ServiceImplementation single_instance = null;
    private BD maBD;

    private ServiceImplementation(BD maBD){
        this.maBD = maBD;
    }

    public static ServiceImplementation getInstance(BD maBD)
    {
        if (single_instance == null)
            single_instance = new ServiceImplementation(maBD);

        return single_instance;
    }


    public void creerQuestion(VDQuestion vdQuestion) throws MauvaiseQuestion {
        // Validation
        if (vdQuestion.texteQuestion == null || vdQuestion.texteQuestion.trim().length() == 0) throw new MauvaiseQuestion("Question vide");
        if (vdQuestion.texteQuestion.trim().length() < 4) throw new MauvaiseQuestion("Question trop courte");
        if (vdQuestion.texteQuestion.trim().length() > 255) throw new MauvaiseQuestion("Question trop longue");
        if (vdQuestion.idQuestion != null) throw new MauvaiseQuestion("Id non nul. La BD doit le gérer");

        // 'il n'y a pas de vote existant pour cette question et cette personne
        // if (vdQuestion.LeVote != null) throw new MauvaiseQuestion("Il ya deja un vote associe a cette question");
        // if (vdQuestion.LeVote.nomVotant != null) throw new MauvaiseQuestion("Il ya deja un votant associe a cette question");


        // Doublon du texte de la question
        for (VDQuestion q : toutesLesQuestions()){
            if (q.texteQuestion.toUpperCase().equals(vdQuestion.texteQuestion.toUpperCase())){
                    throw new MauvaiseQuestion("Question existante");
            }
        }

        // Ajout
        vdQuestion.idQuestion = maBD.monDao().insertQuestion(vdQuestion);
    }

    
    public void creerVote(VDVote vdVote) throws MauvaisVote {
        // Validation
        if (vdVote.nomVotant == null || vdVote.nomVotant.trim().length() == 0) throw new MauvaisVote("Nom du votant inexistant");
        if (vdVote.nomVotant.trim().length() < 4) throw new MauvaisVote("Nom du votant trop court, Minimum quatre caracteres");
        if (vdVote.nomVotant.trim().length() > 256) throw new MauvaisVote("Nom du votant trop long");
        if (vdVote.nbreVote == 0 ) throw new MauvaisVote("On ne peut pas avoir de votes nuls");
        if (vdVote.idVote != null) throw new MauvaisVote("Id non nul. La BD doit le gérer");
        if (vdVote.questionId != null) throw new MauvaisVote("Id non nul. La BD doit le gérer.Il existe deja un vote pour cette question");
        

        // Un votant ne vote pas deux fois pour la meme question

        // 'il n'y a pas de vote existant pour cette question et cette personne

        // vdVote.nomVotant = "null";
        // vdVote.questionId = null;

        // Ajout

        // vdVote.idVote = maBD.monDao().insertVote(vdVote);
    }

    public List<VDQuestion> toutesLesQuestions() {
        //TODO Trier la liste reçue en BD par nombre de votes et la retourner

        return new ArrayList<>();

    }

    
    public float moyenneVotes(VDQuestion question) {
        return 0;
    }

    
    public float ecartTypeVotes(VDQuestion question) {
        return 0;
    }

    
    public Map<Integer, Integer> distributionVotes(VDQuestion question) {
        return null;
    }
}
