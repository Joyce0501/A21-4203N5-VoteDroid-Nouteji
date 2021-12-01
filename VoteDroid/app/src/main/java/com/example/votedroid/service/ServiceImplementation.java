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

    public ServiceImplementation(BD maBD){
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
        for (VDQuestion q : toutesLesQuestions() ){
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

        // verifie si l'id de la question existe
//        boolean idquestionexiste = false;
//        for (VDQuestion V : toutesLesQuestions()) {
//            if (V.idQuestion.equals(vdVote.questionId)){
//                idquestionexiste = true;
//                break;
//            }
//        }
//        if(!idquestionexiste)
//        {
//            throw new MauvaisVote("Id de la question n'existe pas");
//        }
//
        //verifie si un votant est associe a cette question
//        for (VDVote vote : maBD.monDao().tousLesVotesPourUneQuestion(vdVote.questionId)) {
//
//            if(vdVote.nomVotant.toUpperCase().equals(vote.nomVotant.toUpperCase()))
//            {
//                throw new MauvaisVote("Vous avez déja");
//            }
//        }


        // 'il n'y a pas de vote existant pour cette question et cette personne

        for (VDVote v: maBD.monDao().tousLesVotes()){
            if (v.questionId.equals(vdVote.questionId) && v.nomVotant.toUpperCase().equals(vdVote.nomVotant.toUpperCase()) ){
                throw new MauvaisVote("Vous avez deja voté");
            }
        }

        // Ajout
        maBD.monDao().insertVote(vdVote);
    }

    public List<VDQuestion> toutesLesQuestions() {
        //TODO Trier la liste reçue en BD par nombre de votes et la retourner

       List<VDQuestion> listeQuestions = new ArrayList<>(maBD.monDao().toutesLesQuestions());
        return  listeQuestions;

    }

    public void SupprimerVotes()
    {
        maBD.monDao().deleteVotes();
    }

    public void SupprimerQuestions()
    {
        maBD.monDao().deleteQuestions();
    }


    public float moyenneVotes(VDQuestion question) {

         int total =0;
         float moyenne = 0;
        List<VDVote> votes = maBD.monDao().tousLesVotesPourUneQuestion(question.idQuestion);
        for(VDVote vote : votes)
        {
            total += vote.nbreVote;
            moyenne = total / votes.size();
        }
        return moyenne;
    }

    
    public float ecartTypeVotes(VDQuestion question) {

        float total1 = 0;
        float total2 = 0;
        int nbreVote = 0;
        float moyenne = moyenneVotes(question);
        List<VDVote> votes = maBD.monDao().tousLesVotesPourUneQuestion(question.idQuestion);

        for(VDVote vote : votes)
        {
            if(vote.questionId == question.idQuestion)
            {
                total1 += (vote.nbreVote * Math.pow(nbreVote-moyenne,2));
                total2 += vote.nbreVote;
                nbreVote++;
            }

        }
        return (float) Math.sqrt(total1/total2);
    }

    
    public Map<Integer, Integer> distributionVotes(VDQuestion question) {
        return null;
    }
}
