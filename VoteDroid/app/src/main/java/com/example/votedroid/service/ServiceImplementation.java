package com.example.votedroid.service;

import com.example.votedroid.bd.BD;
import com.example.votedroid.exceptions.MauvaisVote;
import com.example.votedroid.exceptions.MauvaiseQuestion;
import com.example.votedroid.exceptions.MauvaiseSuppression;
import com.example.votedroid.modele.VDQuestion;
import com.example.votedroid.modele.VDVote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
        if (vdVote.nbreVote < 0 ) throw new MauvaisVote("Le vote est trop petit");
        if ( vdVote.nbreVote > 5) throw new MauvaisVote("Le vote est trop grand");
        if (vdVote.idVote != null) throw new MauvaisVote("Id non nul. La BD doit le gérer");

        // 'il n'y a pas de vote existant pour cette question et cette personne

        for (VDVote v: maBD.monDao().tousLesVotes()){
            if (v.questionId.equals(vdVote.questionId) && v.nomVotant.toUpperCase().equals(vdVote.nomVotant.toUpperCase()) ){
                throw new MauvaisVote("Vous avez deja voté");
            }
        }

        // Ajout

        vdVote.idVote = maBD.monDao().insertVote(vdVote);
    }

    public List<VDQuestion> toutesLesQuestions() {
        //TODO Trier la liste reçue en BD par nombre de votes et la retourner

       List<VDQuestion> listeQuestions = new ArrayList<>(maBD.monDao().toutesLesQuestions());

        Collections.sort(listeQuestions, new Comparator<VDQuestion>() {
            @Override
            public int compare(VDQuestion V1, VDQuestion V2) {
                return touteslesvotespourunequestion(V2.idQuestion).size() - touteslesvotespourunequestion(V1.idQuestion).size();
            }
        });
         return  listeQuestions;
    }

    public List<VDVote> touteslesvotespourunequestion(Long idQuestion)
    {
       return maBD.monDao().tousLesVotesPourUneQuestion(idQuestion);
    }

    public List<VDVote> touteslesvotes()
    {
        return maBD.monDao().tousLesVotes();
    }

    public void SupprimerVotes() throws MauvaiseSuppression
    {
        if(maBD.monDao().tousLesVotes() == null || maBD.monDao().tousLesVotes().size() == 0) throw new MauvaiseSuppression("Aucun vote existant");
        maBD.monDao().deleteVotes();
    }

    public void SupprimerQuestions() throws MauvaiseSuppression
    {
        if(maBD.monDao().toutesLesQuestions() == null || maBD.monDao().toutesLesQuestions().size() == 0) throw new MauvaiseSuppression("Aucune question existante");
        maBD.monDao().deleteQuestions();
    }

    public float moyenneVotes(VDQuestion question) {
        float total = 0;
        List<VDVote> votes = maBD.monDao().tousLesVotesPourUneQuestion(question.idQuestion);
        for(VDVote vote : votes)
        {
            total += vote.nbreVote;
        }
        return (total / votes.size());
    }

    
    public float ecartTypeVotes(VDQuestion question) {

        float total1 = 0;
        float moyenne = moyenneVotes(question);
        List<VDVote> votes = maBD.monDao().tousLesVotesPourUneQuestion(question.idQuestion);

        for(VDVote vote : votes)
        {
            total1 += Math.pow(vote.nbreVote-moyenne,2);
        }
        return (float) Math.sqrt(total1/votes.size());
    }

    
    public Map<Integer, Integer> distributionVotes(VDQuestion question) {

        Map<Integer, Integer> dataGraph = new HashMap<Integer, Integer>();
        List<VDVote> votes = maBD.monDao().tousLesVotesPourUneQuestion(question.idQuestion);
        int montant = 0;
        for(VDVote unvote : votes)
        {
            dataGraph.put(montant,unvote.nbreVote);
            montant++;
        }
        return dataGraph;
    }
}
