function [ noUse ] = arq( )
  noUse = 0;
  fileId = fopen('ARQtimes.txt','r');
  y = fscanf(fileId, '%i');
  fileId = fopen('ARQpackets.txt','r');
  x = fscanf(fileId, '%i');
  x = x';
  y = y';
  
  plot(x,y);
  set(gca,'FontSize',20)
  title('ARQ packets','FontSize', 30);
  xlabel('Number of packets','FontSize', 30);
  ylabel({'Response time of packets','in milliseconds'},'FontSize', 30);
  axis([0 max(y)/2 (max(y)+max(y)/16)]);

endfunction
