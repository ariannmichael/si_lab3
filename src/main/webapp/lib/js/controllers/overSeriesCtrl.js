angular.module("overSeries").controller("overSeriesCtrl", function($scope, $http) {
  $scope.app = "overSeries";
  $scope.input;
  $scope.searchMade = [];
  $scope.profile = [];
  $scope.watchlist = [];
  $scope.series = [];
  $scope.logon = false;
  $scope.usuario;

  $scope.searchInput = function () {
    if($scope.input === undefined || $scope.input.length === 0) {
      alert('Digite no campo de pesquisa');
    } else {
      var promise = $http.get("https://omdbapi.com/?s=" + $scope.input + "&type=series&apikey=93330d3c").then(function (response) {
        if(response.data.Response === "False") {
          alert('Sua pesquisa não possui resposta');
        } else {
          $scope.searchMade = response.data.Search;
        }
      }, function error (error) {
        console.log(error);
      });
      return promise;
    }
  };

  $scope.addProfile = function (serie) {
    if ($scope.contains($scope.profile, serie)) {
      alert('"' + serie.Title + '" já está no seu perfil')
    } else {
      var promise = $http.get("https://omdbapi.com/?i=" + serie.imdbID + "&plot=full&apikey=93330d3c").then(function(response) {
        serie = response.data;
        serie.avaliacao = "N/A"
        serie.ultimoEpisodio = "N/A"
        $scope.profile.push(serie);
        $scope.addBD(serie, true);
      }).catch(function(error){
        console.log(error);
      });
    }
  };

  $scope.removeProfile = function (serie) {
    if(confirm('Deseja remover "' + serie.Title + '" do seu catálogo?') === true) {
      var index = $scope.profile.indexOf(serie);
      var id = $scope.getIdByImdbID(serie);
      $scope.removeBD(id);
      $scope.profile.splice(index, 1);
    }
  }


  $scope.addWatchlist = function (serie) {
    if($scope.logon === false) {
      alert("Faça login na sua conta");
      return;
    }
    if ($scope.contains($scope.watchlist, serie)) {
      alert('"' + serie.Title + '" já está na sua watchlist!')
    } else if ($scope.contains($scope.profile, serie)) {
      alert('"' + serie.Title + '" já está no seu perfil!')
    } else {
      $scope.watchlist.push(angular.copy(serie));
      $scope.addBD(serie, false);
    }
  };

  $scope.removeWatchlist = function (serie) {
    if (confirm('Deseja remover "' + serie.Title + '" da sua watchlist?') === true) {
      var index = $scope.watchlist.indexOf(serie);
      var id = $scope.getIdByImdbID(serie);
      $scope.removeBD(id);
      $scope.watchlist.splice(index, 1);
    }
  };

  $scope.sendRating = function (serie, rating) {
    serie.avaliacao = rating;
    var serieAtualizada = $scope.getSerieByImdbID(serie);
    console.log(serieAtualizada);
    serieAtualizada.avaliacao = rating;
    $scope.atualizarSerie(serieAtualizada);
  };

  $scope.sendLastEpisode = function (serie, episode) {
    serie.ultimoEpisodio = episode;
    var serieAtualizada = $scope.getSerieByImdbID(serie);
    serieAtualizada.ultimoEpisodio = episode;
    $scope.atualizarSerie(serieAtualizada);
  };

  $scope.atualizarSerie = function(serie) {
    var promise = $http.put("/serie/" + serie.id, serie).then(function(response) {
    }, function error (error) {
      console.log(error);
    });
  };

  $scope.getIdByImdbID = function(serie) {
    for(var i = 0; i < $scope.series.length; i++) {
      if ($scope.series[i].imdbID === serie.imdbID) {
        return $scope.series[i].id;
      }
    }
  };

  $scope.getSerieByImdbID = function(serie) {
    for(var i = 0; i < $scope.series.length; i++) {
      if ($scope.series[i].imdbID === serie.imdbID) {
        return $scope.series[i];
      }
    }
  };

  $scope.contains = function (array, serie) {
    for (var i = 0; i < array.length; i++) {
      if(array[i].imdbID === serie.imdbID) {
        return true;
      }
    }
    return false;
  };
  $('#search-button').on('click', function() {
    $('#search').prop('checked', true);
  });


  $scope.signup = function() {
    var userName = prompt("Name:", "Aramaico")
    var userEmail = prompt("Email:", "aramaico@email.com");
    var userPassword = prompt("Password:", "***********");
    var userRegister = {"nome": userName, "email": userEmail, "senha": userPassword};
    var promise = $http.post("/usuario/", userRegister).then(function(response) {
      if (response.data === "") {
        alert("Email já cadastrado.");
      } else {
        $scope.usuario = response.data;
        $scope.logon = true;
      }
    }, function error (error) {
      console.log(error);
    });
  };

  $scope.login = function() {
    var emailLogin = prompt("Email:", "aramaico@email.com");
    var senhaLogin = prompt("Password:", "***********");
    var userLogin = {nome: "", email: emailLogin, senha: senhaLogin};
    var promise = $http.post("/usuario/login/", userLogin).then(function(response) {
      $scope.usuario = response.data;
      $scope.logon = true;
      $scope.loadSeries();
    }, function error (error) {
      alert("Incorrect.");
      console.log(error);
    });
  };

  $scope.logoff = function() {
    if (confirm("Tem certeza que deseja sair?")) {
      $scope.usuario = undefined;
      $scope.watchlist = [];
      $scope.profile = [];
      $scope.series = [];
      $scope.logon = false;
    }
  };

  $scope.loadSeries = function () {
    console.log($scope.usuario.id);
    var promise = $http.get("/usuario/" + $scope.usuario.id + "/series/").then(function (response) {
      $scope.series = response.data;
      $scope.loadProfileAndWatchlist();
    }, function error (error) {
      console.log(error);
    });
  };

  $scope.loadProfileAndWatchlist = function() {
    for (var i = 0; i < $scope.series.length; i++) {
      if($scope.series[i].noPerfil === true) {
        $scope.loadProfile($scope.series[i]);
      } else {
        $scope.loadWatchlist($scope.series[i].imdbID);
      }
    }
  };

  $scope.loadProfile = function(serie) {
    var promise = $http.get("https://omdbapi.com/?i=" + serie.imdbID + "&plot=full&apikey=93330d3c").then(function(response) {
      serieLoad = response.data;
      serieLoad.avaliacao = serie.avaliacao;
      serieLoad.ultimoEpisodio = serie.ultimoEpisodio;
      $scope.profile.push(serieLoad);
    }).catch(function(error){
      console.log(error);
    });
  };

  $scope.loadWatchlist = function(imdbID) {
    var promise = $http.get("https://omdbapi.com/?i=" + imdbID + "&plot=full&apikey=93330d3c").then(function(response) {
      serieLoad = response.data;
      $scope.watchlist.push(serieLoad);
    }).catch(function(error){
      console.log(error);
    });
  };

  $scope.addBD = function (serie, noPerfil) {
    var serieAddPerfil = {"imdbID": serie.imdbID, "avaliacao": "N/A", "ultimoEpisodio": "N/A", "usuarioID": $scope.usuario.id, "noPerfil": noPerfil};
    var promise = $http.post("/serie/", serieAddPerfil).then(function(response) {
      $scope.series.push(response.data);
    }, function error (error) {
      console.log(error);
    });
  };

  $scope.removeBD = function(id) {
    var promise = $http.delete("/serie/" + id).then(function(response) {
      return response.data;
    }, function error (error) {
      console.log(error);
    });
  };

});
